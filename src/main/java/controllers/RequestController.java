package controllers;

import lombok.RequiredArgsConstructor;
import models.Request;
import models.RequestDetails;
import models.RequestWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import util.CsvFileHandler;

import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class RequestController {


    @GetMapping
    public ResponseEntity<List<Request>> getRequests() throws IOException {
        //TODO add better error handling to this
        try {
            return new ResponseEntity<>(
                    ConvertCsvStringToListOfRequests(CsvFileHandler.readFromCsv()), HttpStatus.OK);
        }
        catch (IOException exc) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error when reading from file", exc);
        }
    }

    @PostMapping
    // The question stated that the date, time, ip information should be input by the user in a POST instead of generated here
    public ResponseEntity<String> saveRequestInformation(@Valid @RequestBody RequestWrapper requestWrapper) throws IOException {
        //TODO Validate the users input
        try {
            CsvFileHandler.writeCsvStringToFile(requestWrapper.getRequest().convertToCsv());
            return new ResponseEntity<>(
                    "Save Successful", HttpStatus.OK);
        }
        catch (IOException exc){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error when reading from file", exc);
        }
    }

    @DeleteMapping("{username}")
    public ResponseEntity deleteRequestDetails(@PathVariable  String username) throws IOException {
        try {
            CsvFileHandler.removeLineFromFile(username);
            return new ResponseEntity<>(
                    "Delete Successful", HttpStatus.OK);
        }
        catch (IOException exc){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error when deleting", exc);
        }
    }

    private List<Request> ConvertCsvStringToListOfRequests(String csvString) throws IOException {
        List<Request> requests = new ArrayList<>();
        List<String> interests = new LinkedList<>(Arrays.asList(csvString.split("\n")));
        for (String request: interests){
            requests.add(new Request().convertToObject(request.split(",")));
        }
        return requests;
    }
}
