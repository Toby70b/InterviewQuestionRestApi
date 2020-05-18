package com.interviewrestapi.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.interviewrestapi.exception.LocationDetailsNotFoundException;
import com.opencsv.bean.CsvRecurse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
public class Request {
    private static final String URI = "http://api.ipstack.com/";
    private static final String KEY = "?access_key=62a441cf871fd83f2bd668bee7b18a5f";

    @NotNull
    @Valid
    @CsvRecurse
    private RequestDetails requestDetails;
    @NotNull
    @Valid
    @CsvRecurse
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
