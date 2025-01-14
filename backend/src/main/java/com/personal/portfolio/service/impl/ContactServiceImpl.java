package com.personal.portfolio.service.impl;

import com.personal.portfolio.entity.Contact;
import com.personal.portfolio.repository.ContactRepository;
import com.personal.portfolio.service.ContactService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    @Transactional
    public Contact save(Contact entity) {
        return contactRepository.save(entity);
    }

    @Override
    @Transactional
    public Contact update(Long id, Contact entity) {
        if (!contactRepository.existsById(id)) {
            throw new EntityNotFoundException("Contact not found with id: " + id);
        }
        entity.setId(id);
        return contactRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Contact contact = findById(id);
        contact.setActive(false);
        contactRepository.save(contact);
    }

    @Override
    public Contact findById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found with id: " + id));
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return contactRepository.existsById(id);
    }

    @Override
    public Page<Contact> getAllContacts(Pageable pageable) {
        return contactRepository.findByActiveTrueOrderByCreatedAtDesc(pageable);
    }

    @Override
    public Page<Contact> getUnreadContacts(Pageable pageable) {
        return contactRepository.findByReadFalseAndActiveTrueOrderByCreatedAtDesc(pageable);
    }

    @Override
    public long getUnreadContactCount() {
        return contactRepository.countByReadFalseAndActiveTrue();
    }

    @Override
    @Transactional
    public Contact markAsRead(Long id) {
        Contact contact = findById(id);
        contact.setRead(true);
        return contactRepository.save(contact);
    }

    @Override
    @Transactional
    public Contact respondToContact(Long id, String response) {
        Contact contact = findById(id);
        contact.setResponse(response);
        contact.setRead(true);
        return contactRepository.save(contact);
    }
} 