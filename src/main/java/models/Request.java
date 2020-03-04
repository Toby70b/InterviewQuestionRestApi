package models;

import misc.Device;
import util.CsvConverter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Request implements CsvConverter {
    private RequestDetails requestDetails;
    private User user;

    public Request() {
    }

    public Request(RequestDetails requestDetails, User user) {
        this.requestDetails = requestDetails;
        this.user = user;
    }

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
        StringBuilder csvCreator = new StringBuilder().append(convertRequestDetailsToCsv()).append(convertUserToCsv()).append("\n");
        return csvCreator.toString();
    }

    @Override
    public Request convertToObject(String[] properties) {
        // Little element of magic number here with the array index, if these were to change would need to change this code
        // TODO look to see if the issue above can be solved
        return new Request(CreateRequestDetailsFromCsv(properties),CreateUserFromCsv(properties));

    }

    private StringBuilder convertUserToCsv() {
        StringBuilder userCsv = new StringBuilder();
        userCsv.append(this.user.getUsername()).append(SEPERATOR);
        userCsv.append(this.user.getName()).append(SEPERATOR);
        String interests = this.user.getInterests().stream().reduce("",(concat,newStr) -> concat+";"+newStr);
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
        String username = properties[4];
        String name  = properties[5];
        List<String> interests = new LinkedList<>(Arrays.asList(properties[6].split(";")));
        //Remove the empty string at start, this is from the reduction method
        //TODO: maybe change the reduction method, this is kinda ugly
        interests.remove(0);

        return new User(name,username,interests);
    }

    private RequestDetails CreateRequestDetailsFromCsv(String[] properties) {
        LocalDate date = LocalDate.parse(properties[0]);
        LocalTime time = LocalTime.parse(properties[1]);
        String ipAddress = properties[2];
        Device device = AddDevice(properties[3]);
        return new RequestDetails(date,time,device,ipAddress);
    }

    private Device AddDevice(String device) {
        switch (device){
            case "PC": return Device.PC;
            case "TABLET": return Device.TABLET;
            case "PHONE": return Device.PHONE;
        }
        throw new IllegalArgumentException("Error, Unexpected Device "+device);
    }
}
