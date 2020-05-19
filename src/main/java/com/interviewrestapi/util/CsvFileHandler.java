package com.interviewrestapi.util;


import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CsvFileHandler<T> {
    private final String fileLocation;
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

    public void removeMatchingIpLogFromFile(List<T> listOfThings, Predicate<T> removalCondition) throws IOException {
        List<T> filteredListOfThings = listOfThings.stream().filter(removalCondition).collect(Collectors.toList());
        writeObjectsToCsv(filteredListOfThings);
    }

    private void writeObjectsToCsv(List<T> listOfObjects) throws IOException {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(fileLocation))){
            StatefulBeanToCsvBuilder<T> builder = new StatefulBeanToCsvBuilder<>(bufferedWriter);
            StatefulBeanToCsv<T> beanWriter = builder.build();
            beanWriter.write(listOfObjects);
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }
}
