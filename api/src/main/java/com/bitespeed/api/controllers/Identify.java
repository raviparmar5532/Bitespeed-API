package com.bitespeed.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.bitespeed.api.dtos.RequestDto;
import com.bitespeed.api.dtos.ResponseDto;
import com.bitespeed.api.models.Contact;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class Identify {
    @PostMapping(value="identify")
    public ResponseEntity<ResponseDto> identifyApi(@RequestBody RequestDto entity) {
        return new ResponseEntity<ResponseDto>(new ResponseDto(new Contact()), HttpStatus.OK);
    }
}
