package com.bitespeed.api.models;

import jakarta.persistence.*;

@Entity
public class PhoneNumber {
    @Id
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "contactId", referencedColumnName = "id")
    private Contact contactId;
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Contact getContactId() {
        return contactId;
    }
    public void setContactId(Contact contactId) {
        this.contactId = contactId;
    }
    
}
