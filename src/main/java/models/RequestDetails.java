package models;
import misc.Device;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class RequestDetails {
    @Id
    private int id;
    private LocalDate date;
    private LocalTime time;
    private Device device;
    private String ipAddress;

    public RequestDetails(int id, LocalDate date, LocalTime time, Device device, String ipAddress) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.device = device;
        this.ipAddress = ipAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
