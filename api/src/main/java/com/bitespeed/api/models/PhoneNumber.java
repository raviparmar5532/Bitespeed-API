package com.bitespeed.api.models;

import jakarta.persistence.*;

@Entity
public class PhoneNumber {
    @Id
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "contactId", referencedColumnName = "id")
    private Contact contactId;
}
