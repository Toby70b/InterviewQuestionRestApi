package controllers;

import lombok.RequiredArgsConstructor;
import models.RequestDetails;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class RequestDetailsController {

    @GetMapping
    public RequestDetails getRequestDetails(){
        return null;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public RequestDetails saveRequestDetails(){
        return null;
    }

    @DeleteMapping
    public RequestDetails deleteRequestDetails(){
        return null;
    }
}
