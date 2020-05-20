package com.interviewrestapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.interviewrestapi.exception.LocationDetailsNotFoundException;
import com.interviewrestapi.exception.NonExistingIpLogException;
import com.interviewrestapi.model.IpDetails;
import com.interviewrestapi.model.IpLog;
import com.interviewrestapi.model.RequestDetails;
import com.interviewrestapi.util.CsvFileHandler;
import com.interviewrestapi.util.HttpRequestCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class IpLogService {

    private static final String URI = "http://api.ipstack.com/";
    private static final String KEY = "?access_key=62a441cf871fd83f2bd668bee7b18a5f";
    private final CsvFileHandler<RequestDetails> requestDetailsCsvFileHandler;
    @Autowired
    public IpLogService(@Value("${ipLogs.IpLogCsvFileLocation}") String prop) throws IOException {
        File file = new ClassPathResource(prop).getFile();
        this.requestDetailsCsvFileHandler = new CsvFileHandler<>(file);
    }

    private List<RequestDetails> filterRequestDetailsByPredicate(List<RequestDetails> ipLogs, Predicate<RequestDetails> predicate) {
        return ipLogs
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    private List<RequestDetails> filterRequestDetailsByUsername(List<RequestDetails> ipLogs, String username) {
        Predicate<RequestDetails> filterByUsername = ipLog -> ipLog.getUser().getUsername().toLowerCase().equals(username.toLowerCase());
        return filterRequestDetailsByPredicate(ipLogs,filterByUsername);
    }

    private List<RequestDetails> filterOutRequestsByUsername(List<RequestDetails> ipLogs, String username) {
        Predicate<RequestDetails> filterOutByUsername = ipLog -> !ipLog.getUser().getUsername().toLowerCase().equals(username.toLowerCase());
        return filterRequestDetailsByPredicate(ipLogs,filterOutByUsername);
    }

    private IpDetails getIpDetailsFromIpStack(String ipAddress) throws IOException {
        HttpRequestCreator httpRequestCreator = new HttpRequestCreator(URI + ipAddress + KEY);
        ObjectMapper mapper = new ObjectMapper();
        //Stack IP Json uses underscores, convert to camelCase here for consistency
        mapper.setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
        IpDetails convertedObject = mapper.readValue(httpRequestCreator.getAll(), IpDetails.class);
        //If the ip type (i.e. ipv4, v6) is null its likely the ip was invalid (I'm not going to try 1000000 ips to confirm this when I'm not being paid :P )
        // Though doing this may be wrong, a user may want the other details but without the ip stuff
        if (convertedObject.getType() == null) throw new LocationDetailsNotFoundException(ipAddress);
        return convertedObject;
    }

    public List<RequestDetails> getRequestDetailsFromCsv() throws IOException {
        return requestDetailsCsvFileHandler.readFromCsv(RequestDetails.class);
    }

    public List<IpLog> getIpLogs(List<RequestDetails> requestDetails) throws IOException {
        List<IpLog> ipLogs = new ArrayList<>();
        for (RequestDetails requestDetail : requestDetails) {
            ipLogs.add(new IpLog(requestDetail, getIpDetailsFromIpStack(requestDetail.getIpAddress())));
        }
        return ipLogs;
    }

    public List<IpLog> getIpLogsByUsername(String username) throws IOException {
        List<RequestDetails> requestDetails = filterRequestDetailsByUsername(getRequestDetailsFromCsv(), username);
        List<IpLog> ipLogs = getIpLogs(requestDetails);
        if (ipLogs.size() <= 0) {
            throw new NonExistingIpLogException(username);
        }
        return ipLogs;
    }

    public void createNewIpLog(RequestDetails requestDetails) throws IOException {
        requestDetailsCsvFileHandler.convertBeanToCsv(requestDetails);
    }

    public void deleteIpLogsByUsername(String username) throws IOException {
        List<RequestDetails> requestDetails = getRequestDetailsFromCsv();
        requestDetailsCsvFileHandler.writeObjectsToCsv(filterOutRequestsByUsername(requestDetails, username));
    }
}
