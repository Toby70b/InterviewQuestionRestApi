package com.interviewrestapi.controller;

import com.interviewrestapi.exception.NonExistingRequestException;
import com.interviewrestapi.util.CsvFileHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.interviewrestapi.model.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Getter
public class RequestController {
    private final CsvFileHandler<Request> csvFileHandler;

    @GetMapping
    public ResponseEntity<? extends List<? extends Object>> listRequests() {
        try {
            return new ResponseEntity<List<? extends Object>>(csvFileHandler.readFromCsv(Request.class), HttpStatus.OK);

        } catch (IOException exc) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error when reading from file", exc);
        }
    }

    @GetMapping("{username}")
    public ResponseEntity<List<Request>> listRequestsByUsername(@PathVariable String username) throws IOException {
       List<Request> requests = filterRequestListByUsername(csvFileHandler.readFromCsv(Request.class), username);
       if (requests.size() <= 0) {
            throw new NonExistingRequestException(username);
       }
       return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PostMapping
    // The question stated that the date, time, ip information should be input by the user in a POST instead of generated here
    public ResponseEntity<String> createRequest(@Valid @RequestBody Request request) {
        try {
            CsvFileHandler<Request> csvFileHandler = new CsvFileHandler<>();
            csvFileHandler.convertBeanToCsv(request);
            return new ResponseEntity<>(
                    "Save Successful", HttpStatus.OK);
        } catch (IOException exc) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error when reading from file", exc);
        }
    }

    @DeleteMapping("{username}")
    public ResponseEntity<String> deleteRequest(@PathVariable String username) {
        try {
            csvFileHandler.removeMatchingRequestsFromFile(csvFileHandler.readFromCsv(Request.class), x -> !x.getUser().getUsername().equals(username));
            return new ResponseEntity<>(
                    "Delete Successful", HttpStatus.OK);
        } catch (IOException exc) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error when deleting", exc);
        }
    }

    private List<Request> filterRequestListByUsername(List<Request> requests, String username){
        return requests
                .stream()
                .filter(request ->  request.getUser().getUsername().toLowerCase().equals(username.toLowerCase()))
                .collect(Collectors.toList());
    }
}
