package com.bitespeed.api.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String phoneNumber;
    private String email;
    private Integer linkedId; // the ID of another Contact linked to this one
    private String linkPrecedence; // "secondary"|"primary" // "primary" if it's the first Contact in the link
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    @OneToMany(mappedBy = "contactId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Email>emails = new ArrayList<>();
    @OneToMany(mappedBy = "contactId", cascade = CascadeType.ALL)
    private List<PhoneNumber>phoneNumbers = new ArrayList<>();
    @OneToMany(mappedBy = "primaryId", cascade = CascadeType.ALL)
    private List<SecondaryPrimaryMapping>secondaries = new ArrayList<>();

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getLinkedId() {
        return linkedId;
    }
    public void setLinkedId(Integer linkedId) {
        this.linkedId = linkedId;
    }
    public String getLinkPrecedence() {
        return linkPrecedence;
    }
    public void setLinkPrecedence(String linkPrecedence) {
        this.linkPrecedence = linkPrecedence;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Date getDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
    public List<Email> getEmails() {
        return emails;
    }
    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }
    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }
    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    public List<SecondaryPrimaryMapping> getSecondaries() {
        return secondaries;
    }
    public void setSecondaries(List<SecondaryPrimaryMapping> secondaries) {
        this.secondaries = secondaries;
    }
    public void addEmail(Email email) {
        this.emails.add(email);
    }
    public void addPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumbers.add(phoneNumber);
    }

    
}
