package com.interviewrestapi.controller;

import com.interviewrestapi.config.Swagger2Config;
import com.interviewrestapi.model.IpLog;
import com.interviewrestapi.model.RequestDetails;
import com.interviewrestapi.services.IpLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/IpLog")
@RequiredArgsConstructor
@Getter
@Api(value = "IpLog management system", tags = {Swagger2Config.IPLOG_CONTROLLER_TAG})
public class IpLogController {

    private final IpLogService ipLogService;

    @ApiOperation(value = "View a list of all IpLog, alongside details of the requests Ip", response = IpLog.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Requests successfully retrieved"),
            @ApiResponse(code = 500, message = "Error when reading from csv file")
    })
    @GetMapping
    public ResponseEntity<List<IpLog>> listIpLogs() {
        try {
            return new ResponseEntity<>(ipLogService.getIpLogs(), HttpStatus.OK);
        } catch (IOException exc) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error when reading from csv file", exc);
        }
    }

    @ApiOperation(value = "View a list of all saved IpLogs, where the username of the IpLog matches the parameter passed", response = IpLog.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "IpLogs successfully retrieved"),
            @ApiResponse(code = 404, message = "IpLog with username not Found"),
            @ApiResponse(code = 500, message = "Error when reading from csv file")
    })
    @GetMapping("{username}")
    public ResponseEntity<List<IpLog>> listIpLogsByUsername(@PathVariable String username) throws IOException {
       return new ResponseEntity<>(ipLogService.getIpLogsByUsername(username), HttpStatus.OK);
    }

    @ApiOperation(value = "Create a new IpLog item", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Save Successful"),
            @ApiResponse(code = 500, message = "Error when reading from csv file")
    })
    @PostMapping
    // The question stated that the date, time, ip information should be input by the user in a POST instead of generated here
    public ResponseEntity<String> createIpLog(@Valid @RequestBody RequestDetails requestDetails) {
        try {
            ipLogService.createNewIpLog(requestDetails);
            return new ResponseEntity<>(
                    "Save Successful", HttpStatus.CREATED);
        } catch (IOException exc) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error when reading from csv file", exc);
        }
    }

    @ApiOperation(value = "Deletes an IpLog item, where the username of the IpLog matches the parameter passed", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete Successful"),
            @ApiResponse(code = 404, message = "IpLog with username not Found"),
            @ApiResponse(code = 500, message = "Error while deleting")
    })
    @DeleteMapping("{username}")
    public ResponseEntity<String> deleteIpLog(@PathVariable String username) {
        try {
            ipLogService.deleteIpLogsByUsername(username);
            return new ResponseEntity<>(
                    "Delete Successful", HttpStatus.OK);
        } catch (IOException exc) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while deleting", exc);
        }
    }


}
