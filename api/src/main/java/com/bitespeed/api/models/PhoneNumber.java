package com.bitespeed.api.models;

import jakarta.persistence.*;

@Entity
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
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
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
}
