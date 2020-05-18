package com.interviewrestapi.controller;

import com.interviewrestapi.exception.NonExistingRequestException;
import com.interviewrestapi.model.Request;
import com.interviewrestapi.util.CsvFileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RequestControllerTest {

    private static final String TEST_CSV = "2019-04-03,13:00,1.1.1.1,TABLET,Toby608b,Toby,Board Games;Video Games;Coding;Scuba Diving," +System.getProperty("line.separator")
            +"2019-04-03,14:15,1.1.1.1,TABLET,Toby608b,Toby,Board Games;Video Games;Coding;Scuba Diving,"+System.getProperty("line.separator")
            +"2019-04-03,13:00,1.1.1.1,TABLET,Toby70b,Toby,Board Games;Video Games;Coding;Scuba Diving,";

    @Mock
    private CsvFileHandler csvFileHandler;

    @InjectMocks
    private RequestController requestController;


    @BeforeEach
    void setUp() {
        requestController = new RequestController(csvFileHandler);
    }

    @Test
    void listRequests() {
    }
/*
    @Test
    void listRequestsByUsernameReturnRequestWhenMatchingUsernameUsed() throws IOException {
        when(requestController.getCsvFileHandler().readFromCsv())
                .thenReturn(TEST_CSV);
        List<Request> requests = requestController.listRequestsByUsername("Toby70b").getBody();
        assertEquals(requests.size(),1);
        assertEquals(requests.get(0).getUser().getUsername(),"Toby70b");

    }

    @Test
    void listRequestsByUsernameReturnMultipleRequestsWhenMatchingUsernameUsed() throws IOException {
        when(requestController.getCsvFileHandler().readFromCsv())
                .thenReturn(TEST_CSV);
        List<Request> requests = requestController.listRequestsByUsername("Toby608b").getBody();
        assertEquals(requests.size(),2);
        assertEquals(requests.get(0).getUser().getUsername(),"Toby608b");
        assertEquals(requests.get(1).getUser().getUsername(),"Toby608b");
    }

    @Test
    void listRequestsByUsernameThrowExceptionWhenNoRequestsWithMatchingUsernameFound() throws IOException {
        when(requestController.getCsvFileHandler().readFromCsv())
                .thenReturn(TEST_CSV);
        assertThrows(NonExistingRequestException.class, () -> {
            requestController.listRequestsByUsername("Toby").getBody();
        });

    }
*/
    @Test
    void createRequestThrowExceptionWhenMethodArgumentsInvalid() {
    }

    @Test
    void createRequestReturnSuccessfullyWhenMethodArgumentsValid() {
    }

}