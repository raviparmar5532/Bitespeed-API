package com.bitespeed.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class SecondaryPrimaryMapping {
    @Id
    @OneToOne
    @JoinColumn(name = "secondaryContactId")
    private Contact secondaryId;

    @ManyToOne
    @JoinColumn(name = "primaryContactId", referencedColumnName = "id")
    private Contact primaryId;

    public Contact getSecondaryId() {
        return secondaryId;
    }

    public void setSecondaryId(Contact secondaryId) {
        this.secondaryId = secondaryId;
    }

    public Contact getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(Contact primaryId) {
        this.primaryId = primaryId;
    }

    
}
