package com.interviewrestapi.customCsvConverters;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalTime;

public class LocalTimeCsvConverter extends AbstractBeanField {
    @Override
    protected Object convert(String localTime) {
        LocalTime parsedTime = LocalTime.parse(localTime);
        return parsedTime;
    }
}