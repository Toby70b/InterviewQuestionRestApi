package com.interviewrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.interviewrestapi.config.Swagger2Config;
import com.interviewrestapi.exception.LocationDetailsNotFoundException;
import com.interviewrestapi.exception.NonExistingIpLogException;
import com.interviewrestapi.model.IpDetails;
import com.interviewrestapi.model.IpLog;
import com.interviewrestapi.model.RequestDetails;
import com.interviewrestapi.util.CsvFileHandler;
import com.interviewrestapi.util.HttpRequestCreator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/IpLog")
@RequiredArgsConstructor
@Getter
@Api(value = "IpLog management system", tags = {Swagger2Config.IPLOG_CONTROLLER_TAG})
public class RequestController {
    private final CsvFileHandler<RequestDetails> requestDetailsCsvFileHandler;
    private static final String URI = "http://api.ipstack.com/";
    private static final String KEY = "?access_key=62a441cf871fd83f2bd668bee7b18a5f";


    @ApiOperation(value = "View a list of all IpLog, alongside details of the requests Ip", response = IpLog.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Requests successfully retrieved"),
            @ApiResponse(code = 500, message = "Error when reading from csv file")
    })
    @GetMapping
    public ResponseEntity<List<IpLog>> listIpLogs() {
        try {
            return new ResponseEntity<>(getIpLogs(), HttpStatus.OK);
        } catch (IOException exc) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error when reading from csv file", exc);
        }
    }

    @ApiOperation(value = "View a list of all saved IpLogs, where the username of the IpLog matches the parameter passed", response = IpLog.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "IpLogs successfully retrieved"),
            @ApiResponse(code = 404, message = "IpLog with username not Found"),
            @ApiResponse(code = 500, message = "Error when reading from csv file")
    })
    @GetMapping("{username}")
    public ResponseEntity<List<IpLog>> listIpLogsByUsername(@PathVariable String username) throws IOException {
       List<IpLog> ipLogs = filterIpLogsByUsername(getIpLogs(), username);
       if (ipLogs.size() <= 0) {
            throw new NonExistingIpLogException(username);
       }
       return new ResponseEntity<>(ipLogs, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a new IpLog item", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Save Successful"),
            @ApiResponse(code = 500, message = "Error when reading from csv file")
    })
    @PostMapping
    // The question stated that the date, time, ip information should be input by the user in a POST instead of generated here
    public ResponseEntity<String> createIpLog(@Valid @RequestBody RequestDetails requestDetails) {
        try {
            CsvFileHandler<RequestDetails> csvFileHandler = new CsvFileHandler<>();
            csvFileHandler.convertBeanToCsv(requestDetails);
            return new ResponseEntity<>(
                    "Save Successful", HttpStatus.CREATED);
        } catch (IOException exc) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error when reading from csv file", exc);
        }
    }

    @ApiOperation(value = "Deletes an IpLog item, where the username of the IpLog matches the parameter passed", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete Successful"),
            @ApiResponse(code = 404, message = "IpLog with username not Found"),
            @ApiResponse(code = 500, message = "Error while deleting")
    })
    @DeleteMapping("{username}")
    public ResponseEntity<String> deleteIpLog(@PathVariable String username) {
        try {
            requestDetailsCsvFileHandler.removeMatchingIpLogFromFile(requestDetailsCsvFileHandler.readFromCsv(IpLog.class), x -> !x.getUser().getUsername().equals(username));
            return new ResponseEntity<>(
                    "Delete Successful", HttpStatus.OK);
        } catch (IOException exc) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while deleting", exc);
        }
    }

    private List<IpLog> filterIpLogsByUsername(List<IpLog> ipLogs, String username){
        return ipLogs
                .stream()
                .filter(ipLog ->  ipLog.getRequestDetails().getUser().getUsername().toLowerCase().equals(username.toLowerCase()))
                .collect(Collectors.toList());
    }

    private IpDetails getIpDetailsFromIpStack(String ipAddress) throws IOException {
        HttpRequestCreator httpRequestCreator = new HttpRequestCreator(URI + ipAddress + KEY);
        ObjectMapper mapper = new ObjectMapper();
        //Stack IP Json uses underscores, convert to camelCase here for consistency
        mapper.setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
        IpDetails convertedObject = mapper.readValue(httpRequestCreator.getAll(), IpDetails.class);
        //If the ip type (i.e. ipv4, v6) is null its likely the ip was invalid (I'm not going to try 1000000 ips to confirm this when I'm not being paid :P )
        // Though doing this may be wrong, a user may want the other details but without the ip stuff
        if(convertedObject.getType() == null) throw new LocationDetailsNotFoundException(ipAddress);
        return convertedObject;
    }

    private List<IpLog> getIpLogs() throws IOException {
        List<IpLog> ipLogs = new ArrayList<>();
        List<RequestDetails> requestDetails = requestDetailsCsvFileHandler.readFromCsv(RequestDetails.class);
        for (RequestDetails requestDetail : requestDetails) {
            ipLogs.add(new IpLog(requestDetail, getIpDetailsFromIpStack(requestDetail.getIpAddress())));
        }
        return ipLogs;
    }
}
