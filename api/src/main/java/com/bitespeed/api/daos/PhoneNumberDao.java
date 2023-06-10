package com.bitespeed.api.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitespeed.api.models.PhoneNumber;


public interface PhoneNumberDao extends JpaRepository<PhoneNumber, Integer> {
    List<PhoneNumber> findByPhoneNumber(String phoneNumber);
}
