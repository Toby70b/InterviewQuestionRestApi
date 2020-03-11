package com.interviewrestapi.controller;

import com.interviewrestapi.errors.ApiError;
import com.interviewrestapi.exception.NonExistingRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class RequestControllerAdvice extends ResponseEntityExceptionHandler {

    @Value("${requests.sendreport.uri}")
    private String sendReportUri;
    @Value("${requests.api.version}")
    private String currentApiVersion;

    @ExceptionHandler(NonExistingRequestException.class)
    public ResponseEntity<ApiError> handleNonExistingRequest(NonExistingRequestException ex) {
        final ApiError error = new ApiError(
                currentApiVersion,
                Integer.toString(HttpStatus.NOT_FOUND.value()),
                "Request Not Found",
                "request-com.interviewrestapi.exceptions",
                "NonExistingRequestException",
                "Request with username "+ex.getMessage()+" Not Found",
                sendReportUri
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //TODO: add sub errors for each field
        List<String> errors = new ArrayList<String>();

        final ApiError error = new ApiError(
                currentApiVersion,
                Integer.toString(BAD_REQUEST.value()),
                "Argument value not valid",
                "request-com.interviewrestapi.exceptions",
                "MethodArgumentNotValidException",
                "Argument value not valid",
                sendReportUri
        );
        return new ResponseEntity<>(error, BAD_REQUEST);
    }
}