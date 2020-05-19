package com.interviewrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NonExistingIpLogException extends RuntimeException {

    public NonExistingIpLogException(final String message) {
        super(message);
    }

    public NonExistingIpLogException() {

    }
}