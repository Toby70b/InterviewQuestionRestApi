package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import misc.Device;
import util.CsvConverter;
import util.HttpRequestCreator;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Request implements CsvConverter {
    private static final String URI = "http://api.ipstack.com/81.153.65.4?access_key=62a441cf871fd83f2bd668bee7b18a5f";

    private RequestDetails requestDetails;
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
        StringBuilder csvCreator = new StringBuilder().append(convertRequestDetailsToCsv()).append(convertUserToCsv()).append("\n");
        return csvCreator.toString();
    }

    @Override
    public Request convertToObject(String[] properties) throws IOException {
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

    private RequestDetails CreateRequestDetailsFromCsv(String[] properties) throws IOException {
            LocalDate date = LocalDate.parse(properties[0]);
            LocalTime time = LocalTime.parse(properties[1]);
            String ipAddress = properties[2];
            LocationDetails locationDetails = getIpAddressDetails();
            Device device = AddDevice(properties[3]);
            return new RequestDetails(date,time,device,ipAddress,locationDetails);
    }

    private LocationDetails getIpAddressDetails() throws IOException {
        HttpRequestCreator httpRequestCreator = new HttpRequestCreator(URI);
        ObjectMapper mapper = new ObjectMapper();
        //Stack IP Json uses underscores, convert to camelCase here for consistency
        mapper.setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
        LocationDetails convertedObject = mapper.readValue(httpRequestCreator.getAll(), LocationDetails.class);
        return convertedObject;
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
