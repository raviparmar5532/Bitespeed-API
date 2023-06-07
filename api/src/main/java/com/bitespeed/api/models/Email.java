package com.bitespeed.api.models;

import jakarta.persistence.*;

@Entity
public class Email {
    @Id
    private String email;
    @ManyToOne
    @JoinColumn(name = "contactId", referencedColumnName = "id")
    private Contact contactId;
}
