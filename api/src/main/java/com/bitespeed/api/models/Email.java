package com.bitespeed.api.models;

import jakarta.persistence.*;

@Entity
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
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
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

}
