package com.interviewrestapi.util;


import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class CsvFileHandler<T> {
    private static final String FILE_LOCATION = ".\\src\\main\\resources\\requests.csv";
    public void convertBeanToCsv(T objectToConvert) throws IOException {
        List<T> arrayList = readFromCsv(objectToConvert.getClass());
        arrayList.add(objectToConvert);
        writeObjectsToCsv(arrayList);
    }

    public List<T> readFromCsv(Class<?> conversionClass) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_LOCATION))) {
            return new CsvToBeanBuilder<T>(bufferedReader).withType((Class<? extends T>) conversionClass).build().parse();
        }
    }

    public void removeMatchingIpLogFromFile(List<T> listOfThings, Predicate<T> removalCondition) throws IOException {
        List<T> filteredListOfThings = listOfThings.stream().filter(removalCondition).collect(Collectors.toList());
        writeObjectsToCsv(filteredListOfThings);
    }

    private void writeObjectsToCsv(List<T> listOfObjects) throws IOException {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(FILE_LOCATION))){
            StatefulBeanToCsvBuilder<T> builder = new StatefulBeanToCsvBuilder<>(bufferedWriter);
            StatefulBeanToCsv<T> beanWriter = builder.build();
            beanWriter.write(listOfObjects);
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }
}
