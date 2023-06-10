package com.bitespeed.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitespeed.api.daos.ContactDao;
import com.bitespeed.api.daos.EmailDao;
import com.bitespeed.api.daos.PhoneNumberDao;


@RestController
public class MyController {
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private EmailDao emailDao;
    @Autowired
    private PhoneNumberDao phoneNumberDao;

    @GetMapping(value="hi")
    public List<String> getMethodName() {
        // return contactDao.findAll().get(0).getEmails().stream().map(e -> e.getEmail()).toList();
        return contactDao.findAll().get(0).getPhoneNumbers().stream().map(e -> e.getPhoneNumber()).toList();
    }
    
}
