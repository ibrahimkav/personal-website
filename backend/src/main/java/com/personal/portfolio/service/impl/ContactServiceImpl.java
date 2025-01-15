package com.personal.portfolio.service.impl;

import com.personal.portfolio.dto.contact.ContactRequest;
import com.personal.portfolio.dto.contact.ContactResponse;
import com.personal.portfolio.dto.contact.ContactReplyRequest;
import com.personal.portfolio.entity.Contact;
import com.personal.portfolio.repository.ContactRepository;
import com.personal.portfolio.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    @Transactional
    public ContactResponse createContact(ContactRequest contactRequest) {
        Contact contact = new Contact();
        mapToContact(contactRequest, contact);
        return mapToContactResponse(contactRepository.save(contact));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactResponse> getAllContacts(Pageable pageable) {
        return contactRepository.findAllByActiveTrueOrderByCreatedAtDesc(pageable)
                .map(this::mapToContactResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactResponse> getUnreadContacts(Pageable pageable) {
        return contactRepository.findAllByActiveTrueAndIsReadFalseOrderByCreatedAtDesc(pageable)
                .map(this::mapToContactResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ContactResponse getContactById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
        return mapToContactResponse(contact);
    }

    @Override
    @Transactional
    public ContactResponse replyToContact(Long id, ContactReplyRequest replyRequest) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
        
        contact.setReplyMessage(replyRequest.getReplyMessage());
        contact.setReplyDate(LocalDateTime.now());
        contact.setIsReplied(true);
        contact.setIsRead(true);
        
        return mapToContactResponse(contactRepository.save(contact));
    }

    @Override
    @Transactional
    public void markAsRead(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
        contact.setIsRead(true);
        contactRepository.save(contact);
    }

    @Override
    @Transactional
    public void deleteContact(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
        contact.setActive(false);
        contactRepository.save(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount() {
        return contactRepository.countByActiveTrueAndIsReadFalse();
    }

    private ContactResponse mapToContactResponse(Contact contact) {
        return ContactResponse.builder()
                .id(contact.getId())
                .name(contact.getName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .subject(contact.getSubject())
                .message(contact.getMessage())
                .isRead(contact.getIsRead())
                .isReplied(contact.getIsReplied())
                .replyMessage(contact.getReplyMessage())
                .replyDate(contact.getReplyDate())
                .createdAt(contact.getCreatedAt())
                .active(contact.getActive())
                .build();
    }

    private void mapToContact(ContactRequest contactRequest, Contact contact) {
        contact.setName(contactRequest.getName());
        contact.setEmail(contactRequest.getEmail());
        contact.setPhone(contactRequest.getPhone());
        contact.setSubject(contactRequest.getSubject());
        contact.setMessage(contactRequest.getMessage());
        contact.setIsRead(false);
        contact.setIsReplied(false);
    }
} 