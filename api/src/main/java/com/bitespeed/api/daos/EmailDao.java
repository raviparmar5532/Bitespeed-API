package com.bitespeed.api.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitespeed.api.models.Email;
import java.util.List;


public interface EmailDao extends JpaRepository<Email, Integer>{
    List<Email> findByEmail(String email);
}
