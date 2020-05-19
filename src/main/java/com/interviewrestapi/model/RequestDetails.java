package com.interviewrestapi.model;

import com.interviewrestapi.customCsvConverters.DeviceCsvConverter;
import com.interviewrestapi.customCsvConverters.LocalDateCsvConverter;
import com.interviewrestapi.customCsvConverters.LocalTimeCsvConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvRecurse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
public class RequestDetails {
    @NonNull
    @NotNull
    @CsvCustomBindByName(converter = LocalDateCsvConverter.class)
    private LocalDate date;
    @NonNull
    @NotNull
    @CsvCustomBindByName(converter = LocalTimeCsvConverter.class)
    private LocalTime time;
    @NonNull
    @NotNull
    @CsvCustomBindByName(converter = DeviceCsvConverter.class)
    private Device device;
    @NonNull
    @NotNull
    @CsvBindByName
    private String ipAddress;
    @NonNull
    @NotNull
    @CsvRecurse
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
