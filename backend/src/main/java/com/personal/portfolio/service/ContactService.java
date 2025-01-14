package com.personal.portfolio.service;

import com.personal.portfolio.entity.Contact;
import com.personal.portfolio.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactService extends BaseService<Contact, Long> {
    Page<Contact> getAllContacts(Pageable pageable);
    
    Page<Contact> getUnreadContacts(Pageable pageable);
    
    long getUnreadContactCount();
    
    Contact markAsRead(Long id);
    
    Contact respondToContact(Long id, String response);
} 