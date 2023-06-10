package com.bitespeed.api.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitespeed.api.models.Email;


public interface EmailDao extends JpaRepository<Email, Integer>{
    List<Email> findByEmail(String email);
}
