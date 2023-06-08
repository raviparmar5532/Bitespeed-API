package com.bitespeed.api.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitespeed.api.models.PhoneNumber;

public interface PhoneNumberDao extends JpaRepository<PhoneNumber, String> {
    
}
