package com.bitespeed.api.services;

import java.util.Date;
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
        Optional<Email>emailOptional = emailDao.findById(email);
        Optional<PhoneNumber>phoneOptional = phoneNumberDao.findById(phoneNumber);
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
            
            emailEntry.setContactId(contactEntry);
            emailDao.save(emailEntry);

            phoneNumberEntry.setContactId(contactEntry);
            phoneNumberDao.save(phoneNumberEntry);
            
            return new ResponseDto(contactEntry);
        }
        //scenario 2: if email or phone any one is found
        if(emailOptional.isPresent() ^ phoneOptional.isPresent()) {

            if(emailOptional.isPresent()) {
                Email emailObj = emailOptional.get();
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

                primaryContact = contactDao.save(primaryContact);

                phoneNumberEntry.setContactId(primaryContact);
                phoneNumberDao.save(phoneNumberEntry);
                
                //Get the id for contact object
                contactEntry = contactDao.save(contactEntry);            

                return new ResponseDto(primaryContact);
            }
            if(phoneOptional.isPresent()) {
                PhoneNumber phoneNumberObj = phoneOptional.get();
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

                primaryContact = contactDao.save(primaryContact);

                emailEntry.setContactId(primaryContact);
                emailDao.save(emailEntry);
                
                //Get the id for contact object
                contactEntry = contactDao.save(contactEntry);            

                return new ResponseDto(primaryContact);
                 
            }
        }
        //scenario 3: if email and phone both are found
        if(emailOptional.isPresent() && phoneOptional.isPresent()) {
            Email emailObj = emailOptional.get();
            PhoneNumber phoneObj = phoneOptional.get();
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
