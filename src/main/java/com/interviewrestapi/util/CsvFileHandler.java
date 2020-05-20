package com.interviewrestapi.util;


import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.List;

@RequiredArgsConstructor
public class CsvFileHandler<T> {

    private final File fileLocation;

    public void convertBeanToCsv(T objectToConvert) throws IOException {
        List<T> arrayList = readFromCsv(objectToConvert.getClass());
        arrayList.add(objectToConvert);
        writeObjectsToCsv(arrayList);
    }

    public List<T> readFromCsv(Class<?> conversionClass) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileLocation))) {
            return new CsvToBeanBuilder<T>(bufferedReader).withType((Class<? extends T>) conversionClass).build().parse();
        }
    }

    public void writeObjectsToCsv(List<T> listOfObjects) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(fileLocation))) {
            StatefulBeanToCsvBuilder<T> builder = new StatefulBeanToCsvBuilder<>(bufferedWriter);
            StatefulBeanToCsv<T> beanWriter = builder.build();
            beanWriter.write(listOfObjects);
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }
}
