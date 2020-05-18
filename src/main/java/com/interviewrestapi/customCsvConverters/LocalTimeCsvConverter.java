package com.interviewrestapi.customCsvConverters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeCsvConverter extends AbstractBeanField {
    @Override
    protected Object convert(String localTime) {
        LocalTime parsedTime = LocalTime.parse(localTime);
        return parsedTime;
    }
}