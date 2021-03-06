package com.interviewrestapi.customCsvConverters;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateCsvConverter extends AbstractBeanField {
    @Override
    protected Object convert(String localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(localDate, formatter);
        return parsedDate;
    }
}
