package com.scm.scm20.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;

public interface ContactService {
    Contact save(Contact contact);
    Contact update(Contact contact);
    List<Contact>getAll();
    Contact getById(String id);
    void delete(String id);

    // search contacts
    Page<Contact>searchByName(String nameKeyword,int size,int page,String sortBy,String order,User user);
    Page<Contact>searchByEmail(String email,int size,int page,String sortBy,String order,User user);
    Page<Contact>searchByPhoneNumber(String phoneNumber,int size,int page,String sortBy,String order,User user);

    // get contacts by userId

    List<Contact>getByUserId(String userId);

    Page<Contact> getByUser(User user,int page,int size,String sortField,String sortDirection);
}
