package com.bitespeed.api.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitespeed.api.models.Email;

public interface EmailDao extends JpaRepository<Email, String>{
    
}
