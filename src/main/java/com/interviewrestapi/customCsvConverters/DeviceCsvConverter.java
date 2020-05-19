package com.interviewrestapi.customCsvConverters;

import com.interviewrestapi.model.Device;
import com.opencsv.bean.AbstractBeanField;

public class DeviceCsvConverter extends AbstractBeanField {
    @Override
    protected Object convert(String device) {
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


