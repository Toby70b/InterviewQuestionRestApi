package models;

import util.CsvConverter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Request implements CsvConverter {
    @Id
    private int id;
    private RequestDetails requestDetails;
    private User user;

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

    @Override
    public Object convertToObject(String csv) {
        return null;
    }
}
