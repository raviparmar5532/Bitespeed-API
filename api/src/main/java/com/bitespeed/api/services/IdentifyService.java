package com.bitespeed.api.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.bitespeed.api.daos.ContactDao;
import com.bitespeed.api.daos.EmailDao;
import com.bitespeed.api.daos.PhoneNumberDao;
import com.bitespeed.api.dtos.ResponseContactDto;
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

    private List<Integer> fetchSecondaries(Contact contact) {
        List<Integer>secondaries = contactDao.findByLinkedId(contact.getId()).stream().map(c -> c.getId()).toList();
        return secondaries;
    }

    public ResponseDto identifyService(String email, String phoneNumber) {
        List<Email> emailsList = emailDao.findByEmail(email);
        List<PhoneNumber> phonesList = phoneNumberDao.findByPhoneNumber(phoneNumber);
        if(email == null && !phonesList.isEmpty()) {
            Contact contact = phoneNumberDao.findByPhoneNumber(phoneNumber).get(0).getContactId();
            return new ResponseContactDto(contact, fetchSecondaries(contact));
        }
        if(phoneNumber == null && !emailsList.isEmpty()) {
            Contact contact = emailDao.findByEmail(email).get(0).getContactId();
            return new ResponseContactDto(contact, fetchSecondaries(contact));
        }
        //scenario 1: if email and phone both not found
        if(emailsList.isEmpty() && phonesList.isEmpty()) {
            //Creation of contact object
            Contact contactEntry = new Contact();
            contactEntry.setEmail(email);
            contactEntry.setPhoneNumber(phoneNumber);
            contactEntry.setLinkPrecedence("primary");

            //Creation of email object
            Email emailEntry = new Email();
            if(email != null) {
                emailEntry.setEmail(email);
                emailEntry = emailDao.save(emailEntry);
                contactEntry.addEmail(emailEntry);
            }
            
            //Creation of phoneNumber object
            PhoneNumber phoneNumberEntry = new PhoneNumber();
            if(phoneNumber != null) {
                phoneNumberEntry.setPhoneNumber(phoneNumber);
                phoneNumberEntry = phoneNumberDao.save(phoneNumberEntry);
                contactEntry.addPhoneNumber(phoneNumberEntry);
            }
                
            //Get the id for contact object
            contactEntry = contactDao.save(contactEntry);
            
            if(email != null) {
                emailEntry.setContactId(contactEntry);
                emailDao.save(emailEntry);
            }
            if(phoneNumber!=null) {    
                phoneNumberEntry.setContactId(contactEntry);
                phoneNumberDao.save(phoneNumberEntry);
            }
            
            return new ResponseContactDto(contactEntry, new ArrayList<>());
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

                //Creation of phoneNumber object
                if(phoneNumber != null) {

                    PhoneNumber phoneNumberEntry = new PhoneNumber();
                    phoneNumberEntry.setPhoneNumber(phoneNumber);
                    primaryContact.addPhoneNumber(phoneNumberEntry);
                    
                    phoneNumberEntry.setContactId(primaryContact);
                    phoneNumberEntry = phoneNumberDao.save(phoneNumberEntry);
                    
                }
                
                //Get the id for contact object
                contactEntry = contactDao.save(contactEntry);            
                
                return new ResponseContactDto(primaryContact, fetchSecondaries(primaryContact));
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
                
                //Creation of email object
                Email emailEntry = new Email();
                emailEntry.setEmail(email);
                primaryContact.addEmail(emailEntry);

                emailEntry.setContactId(primaryContact);
                emailDao.save(emailEntry);
                
                //Get the id for contact object
                contactEntry = contactDao.save(contactEntry);            

                return new ResponseContactDto(primaryContact, fetchSecondaries(primaryContact));
                 
            }
        }
        //scenario 3: if email and phone both are found
        if(!emailsList.isEmpty() && !phonesList.isEmpty()) {
            Email emailObj = emailsList.get(0);
            PhoneNumber phoneObj = phonesList.get(0);

            //scenario 3a: if email and phone both maps with same contact id
            if(emailObj.getContactId().getId() == phoneObj.getContactId().getId()) {
                Contact contactObj = contactDao.getReferenceById(emailObj.getContactId().getId());
                return new ResponseContactDto(contactObj, fetchSecondaries(contactObj));
            }
            //scenario 3b: if email and phone both maps with different contact ids
            if(emailObj.getContactId().getId() != phoneObj.getContactId().getId()) {
                Contact c1 = emailObj.getContactId();
                Contact c2 = phoneObj.getContactId();
                //keep c1 as primary contact
                if(!c1.getCreatedAt().isBefore(c2.getCreatedAt())) {
                    Contact temp = c1;
                    c1 = c2;
                    c2 = temp;
                }
                //update c2 and its child contacts parents
                c2.setLinkPrecedence("Secondary");
                c2.setLinkedId(c1.getId());
                for(Contact child : contactDao.findByLinkedId(c2.getId())) {
                    child.setLinkedId(c1.getId());
                    contactDao.save(child);
                }
                for(Email childEmail : c2.getEmails()) {
                    childEmail.setContactId(c1);
                    emailDao.save(childEmail);
                }
                for(PhoneNumber childPhoneNumber : c2.getPhoneNumbers()) {
                    childPhoneNumber.setContactId(c1);
                    phoneNumberDao.save(childPhoneNumber);
                }
                c2 = contactDao.save(c2);
                return new ResponseContactDto(c1, fetchSecondaries(c1));
            }
        }
        return null;
    }
}
