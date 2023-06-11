package com.bitespeed.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bitespeed.api.dtos.RequestDto;
import com.bitespeed.api.dtos.ResponseDto;
import com.bitespeed.api.dtos.ResponseMessageDto;
import com.bitespeed.api.services.IdentifyService;


@RestController
public class IdentifyController {
    @Autowired
    private IdentifyService identifyService;
    @PostMapping(value="identify")
    public ResponseEntity<ResponseDto> identifyApi(@RequestBody RequestDto request) {
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();
        if(email == null && phoneNumber == null) {
            return new ResponseEntity<>(new ResponseMessageDto("At least one field is required in the request body"), null, HttpStatus.BAD_REQUEST);
        }
        ResponseDto response = identifyService.identifyService(email, phoneNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
