package com.bitespeed.api.services;

import java.util.Date;
import java.util.Optional;

import org.hibernate.type.descriptor.java.ZonedDateTimeJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitespeed.api.daos.ContactDao;
import com.bitespeed.api.daos.EmailDao;
import com.bitespeed.api.daos.PhoneNumberDao;
import com.bitespeed.api.dtos.ResponseDto;
import com.bitespeed.api.models.Contact;
import com.bitespeed.api.models.Email;
import com.bitespeed.api.models.PhoneNumber;

@Service
public class IdentifyService {
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private EmailDao emailDao;
    @Autowired
    private PhoneNumberDao phoneNumberDao;

    public ResponseDto identifyService(String email, String phoneNumber) {
        Optional<Email>emailOptional = emailDao.findById(email);
        Optional<Email>phoneOptional = emailDao.findById(phoneNumber);
        //scenario 1: if email and phone both not found
        if(!emailOptional.isPresent() && !phoneOptional.isPresent()) {
            //Creation of contact object
            Contact contactEntry = new Contact();
            contactEntry.setEmail(email);
            contactEntry.setPhoneNumber(phoneNumber);
            contactEntry.setLinkPrecedence("primary");
            contactEntry.setCreatedAt(new Date());
            contactEntry.setUpdatedAt(new Date());

            //Creation of email object
            Email emailEntry = new Email();
            emailEntry.setEmail(email);
            contactEntry.addEmail(emailEntry);
            
            //Creation of phoneNumber object
            PhoneNumber phoneNumberEntry = new PhoneNumber();
            phoneNumberEntry.setPhoneNumber(phoneNumber);
            contactEntry.addPhoneNumber(phoneNumberEntry);
            
            //Get the id for contact object
            contactEntry = contactDao.save(contactEntry);
            
            for (Email emailObj : contactEntry.getEmails()) {
                emailObj.setContactId(contactEntry);
                emailDao.save(emailObj);
            }
            for (PhoneNumber phoneNumberObj : contactEntry.getPhoneNumbers()) {
                phoneNumberObj.setContactId(contactEntry);
                phoneNumberDao.save(phoneNumberObj);
            }
            
            return new ResponseDto(contactEntry);
        }
        //scenario 2: if email or phone any one is found
        if(emailOptional.isPresent() && phoneOptional.isPresent()) {
            return new ResponseDto(emailDao.getReferenceById(email).getContactId());
        }
        return new ResponseDto(new Contact());
    }
}
