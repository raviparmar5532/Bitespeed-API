package com.bitespeed.api.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitespeed.api.models.Contact;

public interface ContactDao extends JpaRepository<Contact, Integer> {
    
}
