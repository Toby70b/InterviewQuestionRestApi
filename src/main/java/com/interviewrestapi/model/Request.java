package com.interviewrestapi.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.interviewrestapi.exception.LocationDetailsNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.interviewrestapi.util.CsvConverter;
import com.interviewrestapi.util.HttpRequestCreator;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class Request implements CsvConverter {
    private static final String URI = "http://api.ipstack.com/";
    private static final String KEY = "?access_key=62a441cf871fd83f2bd668bee7b18a5f";

    //RequestDetails properties Index constants
    private static final int DATE_INDEX = 0;
    private static final int TIME_INDEX = 1;
    private static final int IPADDRESS_INDEX = 2;
    private static final int DEVICE_INDEX = 3;

    //User properties Index constants
    private static final int USERNAME_INDEX = 4;
    private static final int NAME_INDEX = 5;
    private static final int INTERESTS_INDEX = 6;

    @NotNull
    @Valid
    private RequestDetails requestDetails;
    @NotNull
    @Valid
    private User user;

    public RequestDetails getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(RequestDetails requestDetails) {
        this.requestDetails = requestDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String convertToCsv() {
        StringBuilder csvCreator = new StringBuilder().append(convertRequestDetailsToCsv()).append(convertUserToCsv()).append(System.lineSeparator());
        return csvCreator.toString();
    }

    @Override
    public Request convertToObject(String[] properties) throws IOException {
        return new Request(CreateRequestDetailsFromCsv(properties), CreateUserFromCsv(properties));
    }

    private StringBuilder convertUserToCsv() {
        StringBuilder userCsv = new StringBuilder();
        userCsv.append(this.user.getUsername()).append(SEPERATOR);
        userCsv.append(this.user.getName()).append(SEPERATOR);
        String interests = this.user.getInterests().stream().collect(Collectors.joining(";"));
        userCsv.append(interests).append(SEPERATOR);
        return userCsv;
    }

    private StringBuilder convertRequestDetailsToCsv() {
        StringBuilder requestDetailsCsv = new StringBuilder();
        requestDetailsCsv.append(this.requestDetails.getDate().toString()).append(SEPERATOR);
        requestDetailsCsv.append(this.requestDetails.getTime().toString()).append(SEPERATOR);
        requestDetailsCsv.append(this.requestDetails.getIpAddress()).append(SEPERATOR);
        requestDetailsCsv.append(this.requestDetails.getDevice().toString()).append(SEPERATOR);
        return requestDetailsCsv;
    }

    private User CreateUserFromCsv(String[] properties) {
        String username = properties[USERNAME_INDEX];
        String name = properties[NAME_INDEX];
        List<String> interests = new LinkedList<>(Arrays.asList(properties[INTERESTS_INDEX].split(";")));
        return new User(name, username, interests);
    }

    private RequestDetails CreateRequestDetailsFromCsv(String[] properties) throws IOException {
        LocalDate date = LocalDate.parse(properties[DATE_INDEX]);
        LocalTime time = LocalTime.parse(properties[TIME_INDEX]);
        String ipAddress = properties[IPADDRESS_INDEX];
        LocationDetails locationDetails = getIpAddressDetails(properties[IPADDRESS_INDEX]);
        Device device = AddDevice(properties[DEVICE_INDEX]);
        return new RequestDetails(date, time, device, ipAddress, locationDetails);
    }

    private LocationDetails getIpAddressDetails(String ipAddress) throws IOException {
        HttpRequestCreator httpRequestCreator = new HttpRequestCreator(URI + ipAddress + KEY);
        ObjectMapper mapper = new ObjectMapper();
        //Stack IP Json uses underscores, convert to camelCase here for consistency
        mapper.setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
        LocationDetails convertedObject = mapper.readValue(httpRequestCreator.getAll(), LocationDetails.class);
        //If the ip type (i.e. ipv4, v6) is null its likely the ip was invalid (I'm not going to try 1000000 ips to confirm this when I'm not being paid)
        // Though doing this may be wrong, a user may want the other details but without the ip stuff
        if(convertedObject.getType() == null) throw new LocationDetailsNotFoundException(ipAddress);
        return convertedObject;
    }

    private Device AddDevice(String device) {
        switch (device) {
            case "PC":
                return Device.PC;
            case "TABLET":
                return Device.TABLET;
            case "PHONE":
                return Device.PHONE;
        }
        throw new IllegalArgumentException("Error, Unexpected Device " + device);
    }
}
