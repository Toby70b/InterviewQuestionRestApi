package controllers;

import lombok.RequiredArgsConstructor;
import models.Request;
import models.RequestDetails;
import models.RequestWrapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class RequestController {

    @GetMapping
    public RequestDetails getRequestDetails(){
        System.out.println("test");return null;
    }

    @PostMapping
    public ResponseEntity<Request> saveRequestInformation(@RequestBody RequestWrapper request){
        //TODO Validate the users input
        request.getRequest().getUser().getName();
        return null;
    }

    @DeleteMapping
    public RequestDetails deleteRequestDetails(){
        return null;
    }
}
