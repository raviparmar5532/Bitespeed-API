package com.bitespeed.api.dtos;

import java.util.List;
import com.bitespeed.api.models.Contact;

public class ResponseDto {

    public ResponseDto() {}
    
    public ResponseDto(Contact contact) {
        primaryContatctId = contact.getId();
        this.phoneNumbers = contact.getPhoneNumbers().stream().map(p -> p.getPhoneNumber()).toList();
        this.emails = contact.getEmails().stream().map(e -> e.getEmail()).toList();
        secondaryContactIds = contact.getSecondaries().stream().map(s -> s.getSecondaryId().getId()).toList();
    }
    
    private Integer primaryContatctId;
    private List<String> phoneNumbers;
    private List<String> emails;
    private List<Integer> secondaryContactIds;
    
    public Integer getPrimaryContatctId() {
        return primaryContatctId;
    }

    public void setPrimaryContatctId(Integer primaryContatctId) {
        this.primaryContatctId = primaryContatctId;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<Integer> getSecondaryContactIds() {
        return secondaryContactIds;
    }

    public void setSecondaryContactIds(List<Integer> secondaryContactIds) {
        this.secondaryContactIds = secondaryContactIds;
    }

    
}
