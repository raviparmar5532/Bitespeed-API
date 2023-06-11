package com.bitespeed.api.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitespeed.api.models.Contact;
import java.util.List;


public interface ContactDao extends JpaRepository<Contact, Integer> {
    List<Contact> findByLinkedId(Integer linkedId);
}
