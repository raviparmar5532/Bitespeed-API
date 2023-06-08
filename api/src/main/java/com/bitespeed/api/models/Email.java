package com.bitespeed.api.models;

import jakarta.persistence.*;

@Entity
public class Email {
    @Id
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contactId", referencedColumnName = "id")
    private Contact contactId;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Contact getContactId() {
        return contactId;
    }
    public void setContactId(Contact contactId) {
        this.contactId = contactId;
    }

}
