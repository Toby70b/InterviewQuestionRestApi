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

@AllArgsConstructor
@NoArgsConstructor
public class IpLog {
    @NotNull
    @Valid
    @CsvRecurse
    private RequestDetails requestDetails;
    @NotNull
    @Valid
    @CsvRecurse
    private IpDetails ipDetails;

    public RequestDetails getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(RequestDetails requestDetails) {
        this.requestDetails = requestDetails;
    }

    public IpDetails getIpDetails() {
        return ipDetails;
    }

    public void setIpDetails(IpDetails ipDetails) {
        this.ipDetails = ipDetails;
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
