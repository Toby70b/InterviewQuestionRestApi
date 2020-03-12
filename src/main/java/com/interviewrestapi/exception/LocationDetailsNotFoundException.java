package com.interviewrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LocationDetailsNotFoundException extends RuntimeException {
    public LocationDetailsNotFoundException(final String message) {
        super(message);
    }

    public LocationDetailsNotFoundException() {

    }
}



