package com.bitespeed.api.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitespeed.api.models.PhoneNumber;
import java.util.List;


public interface PhoneNumberDao extends JpaRepository<PhoneNumber, Integer> {
    List<PhoneNumber> findByPhoneNumber(String phoneNumber);
}
