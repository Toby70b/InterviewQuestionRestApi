package com.interviewrestapi.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Error {
    private  String domain;
    private  String reason;
    private  String message;
}