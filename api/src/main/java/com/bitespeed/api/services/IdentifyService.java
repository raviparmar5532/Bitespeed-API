package com.bitespeed.api.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        List<Email> emailsList = emailDao.findByEmail(email);
        List<PhoneNumber> phonesList = phoneNumberDao.findByPhoneNumber(phoneNumber);
        //scenario 1: if email and phone both not found
        if(emailsList.isEmpty() && phonesList.isEmpty()) {
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
            emailEntry = emailDao.save(emailEntry);
            contactEntry.addEmail(emailEntry);
            
            //Creation of phoneNumber object
            PhoneNumber phoneNumberEntry = new PhoneNumber();
            phoneNumberEntry.setPhoneNumber(phoneNumber);
            phoneNumberEntry = phoneNumberDao.save(phoneNumberEntry);
            contactEntry.addPhoneNumber(phoneNumberEntry);
            
            //Get the id for contact object
            contactEntry = contactDao.save(contactEntry);
            
            emailEntry.setContactId(contactEntry);
            emailDao.save(emailEntry);

            phoneNumberEntry.setContactId(contactEntry);
            phoneNumberDao.save(phoneNumberEntry);
            
            return new ResponseDto(contactEntry);
        }
        //scenario 2: if email or phone any one is found
        if(emailsList.isEmpty() ^ phonesList.isEmpty()) {

            if(!emailsList.isEmpty()) {
                Email emailObj = emailsList.get(0);
                Contact primaryContact = emailObj.getContactId();
                
                //Creation of contact object
                Contact contactEntry = new Contact();
                contactEntry.setEmail(email);
                contactEntry.setPhoneNumber(phoneNumber);
                contactEntry.setLinkedId(primaryContact.getId());
                contactEntry.setLinkPrecedence("Secondary");
                contactEntry.setCreatedAt(new Date());
                contactEntry.setUpdatedAt(new Date());

                //Creation of phoneNumber object
                PhoneNumber phoneNumberEntry = new PhoneNumber();
                phoneNumberEntry.setPhoneNumber(phoneNumber);
                primaryContact.addPhoneNumber(phoneNumberEntry);

                phoneNumberEntry.setContactId(primaryContact);
                phoneNumberDao.save(phoneNumberEntry);
                
                //Get the id for contact object
                contactEntry = contactDao.save(contactEntry);            

                return new ResponseDto(primaryContact);
            }
            if(!phonesList.isEmpty()) {
                PhoneNumber phoneNumberObj = phonesList.get(0);
                Contact primaryContact = phoneNumberObj.getContactId();

                //Creation of contact object
                Contact contactEntry = new Contact();
                contactEntry.setEmail(email);
                contactEntry.setPhoneNumber(phoneNumber);
                contactEntry.setLinkedId(primaryContact.getId());
                contactEntry.setLinkPrecedence("Secondary");
                contactEntry.setCreatedAt(new Date());
                contactEntry.setUpdatedAt(new Date());
                
                //Creation of email object
                Email emailEntry = new Email();
                emailEntry.setEmail(email);
                primaryContact.addEmail(emailEntry);

                emailEntry.setContactId(primaryContact);
                emailDao.save(emailEntry);
                
                //Get the id for contact object
                contactEntry = contactDao.save(contactEntry);            

                return new ResponseDto(primaryContact);
                 
            }
        }
        //scenario 3: if email and phone both are found
        if(!emailsList.isEmpty() && !phonesList.isEmpty()) {
            Email emailObj = emailsList.get(0);
            PhoneNumber phoneObj = phonesList.get(0);
            //scenario 3a: if email and phone both maps with same contact id
            if(emailObj.getContactId().getId() == phoneObj.getContactId().getId()) {
                Contact contactObj = contactDao.getReferenceById(emailObj.getContactId().getId());
                return new ResponseDto(contactObj);
            }
            //scenario 3b: if email and phone both maps with different contact ids
        }
        return new ResponseDto(new Contact());
    }
}
