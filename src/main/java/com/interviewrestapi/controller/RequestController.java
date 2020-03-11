package com.interviewrestapi.controller;

import com.interviewrestapi.exception.NonExistingRequestException;
import lombok.RequiredArgsConstructor;
import com.interviewrestapi.model.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.interviewrestapi.util.CsvFileHandler;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RequestController {
    @GetMapping
    public ResponseEntity<List<Request>> listRequests() throws IOException {
        try {
            return new ResponseEntity<>(
                    ConvertCsvStringToListOfRequests(CsvFileHandler.readFromCsv()), HttpStatus.OK);

        } catch (IOException exc) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error when reading from file", exc);
        }
    }

    @GetMapping("{username}")
    public ResponseEntity<List<Request>> listRequestsByUsername(@PathVariable String username) throws IOException {
        List<Request> requests = filterRequestListByUsername(ConvertCsvStringToListOfRequests(CsvFileHandler.readFromCsv()), username);
        if (requests.size() <= 0) {
            throw new NonExistingRequestException(username);
        }
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PostMapping
    // The question stated that the date, time, ip information should be input by the user in a POST instead of generated here
    public ResponseEntity<String> createRequest(@Valid @RequestBody Request request) throws IOException {
        try {
            CsvFileHandler.writeCsvStringToFile(request.convertToCsv());
            return new ResponseEntity<>(
                    "Save Successful", HttpStatus.OK);
        } catch (IOException exc) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error when reading from file", exc);
        }
    }

    @DeleteMapping("{username}")
    public ResponseEntity deleteRequest(@PathVariable String username) throws IOException {
        try {
            CsvFileHandler.removeLineFromFile(username);
            return new ResponseEntity<>(
                    "Delete Successful", HttpStatus.OK);
        } catch (IOException exc) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error when deleting", exc);
        }
    }

    private List<Request> ConvertCsvStringToListOfRequests(String csvString) throws IOException {
        List<Request> requests = new ArrayList<>();
        List<String> interests = new LinkedList<>(Arrays.asList(csvString.split(System.lineSeparator())));
        for (String request : interests) {
            requests.add(new Request().convertToObject(request.split(",")));
        }
        return requests;
    }

    private List<Request> filterRequestListByUsername(List<Request> requests, String username){
        return requests
                .stream()
                .filter(request ->  request.getUser().getUsername().toLowerCase().equals(username.toLowerCase()))
                .collect(Collectors.toList());
    }
}
