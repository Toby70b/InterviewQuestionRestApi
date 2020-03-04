package models;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import misc.Device;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@AllArgsConstructor
public class RequestDetails {
    @NonNull
    private LocalDate date;
    @NonNull
    private LocalTime time;
    @NonNull
    private Device device;
    @NonNull
    private String ipAddress;
    private LocationDetails locationDetails;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocationDetails getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(LocationDetails locationDetails) {
        this.locationDetails = locationDetails;
    }
}
