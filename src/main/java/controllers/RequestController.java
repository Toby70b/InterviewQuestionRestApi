package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import models.Request;
import models.RequestDetails;
import models.RequestWrapper;
import models.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import util.CsvConverter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class RequestController {

    private static final String FILE_LOCATION = "C:\\Users\\Toby Peel\\DEV\\Java Projects\\InterviewQuestionRestApi\\src\\main\\resources\\requests.csv";

    @GetMapping
    public RequestDetails getRequestDetails(){
        System.out.println("test");return null;
    }

    @PostMapping
    // The question stated that the date, time, ip information should be input by the user in a POST instead of generated here
    public ResponseEntity<Request> saveRequestInformation(@RequestBody RequestWrapper requestWrapper) throws IOException {
        //TODO Validate the users input
        RequestDetails requestDetailsCsv = requestWrapper.getRequest().getRequestDetails();
        User UserCsv = requestWrapper.getRequest().getUser();
        WriteCsvValuesToFile(ConvertToCsv(requestDetailsCsv),ConvertToCsv(UserCsv));
        return null;
    }

    private void WriteCsvValuesToFile(String requestDetailsCsv, String UserCsv) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_LOCATION,true));
        writer.write(requestDetailsCsv);
        writer.write(UserCsv);
        writer.close();
    }

    private String ConvertToCsv(Object object) throws JsonProcessingException {
        return  CsvConverter.convertObjectToCSV(object);
    }

    @DeleteMapping
    public RequestDetails deleteRequestDetails(){
        return null;
    }
}
