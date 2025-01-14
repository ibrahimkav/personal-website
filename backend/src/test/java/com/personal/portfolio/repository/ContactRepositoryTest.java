package com.personal.portfolio.repository;

import com.personal.portfolio.entity.Contact;
import com.personal.portfolio.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ContactRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContactRepository contactRepository;

    private Contact testContact;
    private Contact readContact;
    private Contact unreadContact;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testContact = TestUtil.createTestContact();
        testContact.setActive(true);
        entityManager.persist(testContact);

        readContact = TestUtil.createTestContact();
        readContact.setRead(true);
        readContact.setActive(true);
        entityManager.persist(readContact);

        unreadContact = TestUtil.createTestContact();
        unreadContact.setRead(false);
        unreadContact.setActive(true);
        entityManager.persist(unreadContact);

        pageable = PageRequest.of(0, 10);
        entityManager.flush();
    }

    @Test
    void findByActiveTrueOrderByCreatedAtDesc_ShouldReturnAllActiveContacts() {
        Page<Contact> result = contactRepository.findByActiveTrueOrderByCreatedAtDesc(pageable);

        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
    }

    @Test
    void findByReadFalseAndActiveTrueOrderByCreatedAtDesc_ShouldReturnUnreadActiveContacts() {
        Page<Contact> result = contactRepository.findByReadFalseAndActiveTrueOrderByCreatedAtDesc(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertFalse(result.getContent().get(0).getRead());
    }

    @Test
    void findByIdAndActiveTrue_ShouldReturnContact_WhenExists() {
        Optional<Contact> result = contactRepository.findByIdAndActiveTrue(testContact.getId());

        assertTrue(result.isPresent());
        assertEquals(testContact.getName(), result.get().getName());
        assertEquals(testContact.getEmail(), result.get().getEmail());
    }

    @Test
    void findByIdAndActiveTrue_ShouldReturnEmpty_WhenNotExists() {
        testContact.setActive(false);
        entityManager.persist(testContact);
        entityManager.flush();

        Optional<Contact> result = contactRepository.findByIdAndActiveTrue(testContact.getId());

        assertFalse(result.isPresent());
    }

    @Test
    void save_ShouldPersistContact() {
        Contact newContact = TestUtil.createTestContact();
        newContact.setName("New Name");
        newContact.setEmail("new@example.com");

        Contact savedContact = contactRepository.save(newContact);
        entityManager.flush();

        Contact foundContact = entityManager.find(Contact.class, savedContact.getId());
        assertNotNull(foundContact);
        assertEquals(newContact.getName(), foundContact.getName());
        assertEquals(newContact.getEmail(), foundContact.getEmail());
    }

    @Test
    void update_ShouldUpdateContact() {
        testContact.setName("Updated Name");
        testContact.setEmail("updated@example.com");

        Contact updatedContact = contactRepository.save(testContact);
        entityManager.flush();

        Contact foundContact = entityManager.find(Contact.class, updatedContact.getId());
        assertEquals("Updated Name", foundContact.getName());
        assertEquals("updated@example.com", foundContact.getEmail());
    }
} 