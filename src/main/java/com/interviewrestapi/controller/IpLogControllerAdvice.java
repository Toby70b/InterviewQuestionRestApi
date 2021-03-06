package com.interviewrestapi.controller;

import com.interviewrestapi.errors.ApiError;
import com.interviewrestapi.errors.Error;
import com.interviewrestapi.exception.LocationDetailsNotFoundException;
import com.interviewrestapi.exception.NonExistingIpLogException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class IpLogControllerAdvice extends ResponseEntityExceptionHandler {

    @Value("${ipLogs.api.version}")
    private String currentApiVersion;

    @ExceptionHandler(NonExistingIpLogException.class)
    public ResponseEntity<ApiError> handleNonExistingRequest(NonExistingIpLogException ex) {
        final ApiError error = new ApiError(
                currentApiVersion,
                Integer.toString(HttpStatus.NOT_FOUND.value()),
                "Request Not Found",
                "request-exceptions",
                "NonExistingRequestException",
                "Request with username " + ex.getMessage() + " Not Found"
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LocationDetailsNotFoundException.class)
    public ResponseEntity<ApiError> handleLocationDetailsNotFound(LocationDetailsNotFoundException ex) {
        final ApiError error = new ApiError(
                currentApiVersion,
                Integer.toString(HttpStatus.NOT_FOUND.value()),
                "Location details not found",
                "request-exceptions",
                "LocationDetailsNotFoundException",
                "IP stack couldn't find any details for IP " + ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ApiError apiError = new ApiError(
                currentApiVersion,
                Integer.toString(BAD_REQUEST.value()),
                "Argument value not valid",
                "request-exceptions",
                "MethodArgumentNotValidException",
                "Argument value not valid"
        );
        List<Error> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(new Error("request-exceptions", "MethodArgumentNotValidException", error.getField() + ": " + error.getDefaultMessage()));
        }
        apiError.getError().setErrors(errors);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }
}