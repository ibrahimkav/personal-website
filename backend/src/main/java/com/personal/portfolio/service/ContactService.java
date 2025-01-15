package com.personal.portfolio.service;

import com.personal.portfolio.dto.contact.ContactRequest;
import com.personal.portfolio.dto.contact.ContactResponse;
import com.personal.portfolio.dto.contact.ContactReplyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactService {
    ContactResponse createContact(ContactRequest contactRequest);
    Page<ContactResponse> getAllContacts(Pageable pageable);
    Page<ContactResponse> getUnreadContacts(Pageable pageable);
    ContactResponse getContactById(Long id);
    ContactResponse replyToContact(Long id, ContactReplyRequest replyRequest);
    void markAsRead(Long id);
    void deleteContact(Long id);
    long getUnreadCount();
} 