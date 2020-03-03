package com.interviewrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"controllers"} )
public class InterviewRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewRestApiApplication.class, args);
    }

}
