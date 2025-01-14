package com.personal.portfolio.service;

import com.personal.portfolio.entity.Contact;
import com.personal.portfolio.exception.EntityNotFoundException;
import com.personal.portfolio.repository.ContactRepository;
import com.personal.portfolio.service.impl.ContactServiceImpl;
import com.personal.portfolio.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    private Contact testContact;
    private List<Contact> testContacts;
    private Page<Contact> testContactPage;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testContact = TestUtil.createTestContact();
        
        Contact readContact = TestUtil.createTestContact();
        readContact.setRead(true);
        
        Contact unreadContact = TestUtil.createTestContact();
        unreadContact.setRead(false);
        
        testContacts = Arrays.asList(readContact, unreadContact);
        pageable = PageRequest.of(0, 10);
        testContactPage = new PageImpl<>(testContacts, pageable, testContacts.size());
    }

    @Test
    void getAllContacts_ShouldReturnContactPage() {
        when(contactRepository.findAllByActiveTrue(any(Pageable.class)))
                .thenReturn(testContactPage);

        Page<Contact> result = contactService.getAllContacts(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(contactRepository, times(1)).findAllByActiveTrue(pageable);
    }

    @Test
    void getUnreadContacts_ShouldReturnUnreadContactPage() {
        when(contactRepository.findAllByReadFalseAndActiveTrue(any(Pageable.class)))
                .thenReturn(testContactPage);

        Page<Contact> result = contactService.getUnreadContacts(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(contactRepository, times(1)).findAllByReadFalseAndActiveTrue(pageable);
    }

    @Test
    void getContactById_ShouldReturnContact_WhenExists() {
        when(contactRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testContact));

        Contact result = contactService.getContactById(1L);

        assertNotNull(result);
        assertEquals(testContact.getName(), result.getName());
        assertEquals(testContact.getEmail(), result.getEmail());
        verify(contactRepository, times(1)).findByIdAndActiveTrue(1L);
    }

    @Test
    void getContactById_ShouldThrowException_WhenNotExists() {
        when(contactRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> contactService.getContactById(1L));
        verify(contactRepository, times(1)).findByIdAndActiveTrue(1L);
    }

    @Test
    void submitContact_ShouldSubmitAndReturnContact() {
        when(contactRepository.save(any(Contact.class))).thenReturn(testContact);

        Contact result = contactService.submitContact(testContact);

        assertNotNull(result);
        assertEquals(testContact.getName(), result.getName());
        assertEquals(testContact.getEmail(), result.getEmail());
        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void markAsRead_ShouldMarkContactAsRead_WhenExists() {
        when(contactRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testContact));
        when(contactRepository.save(any(Contact.class))).thenReturn(testContact);

        Contact result = contactService.markAsRead(1L);

        assertNotNull(result);
        assertTrue(result.isRead());
        verify(contactRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void markAsRead_ShouldThrowException_WhenNotExists() {
        when(contactRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> contactService.markAsRead(1L));
        verify(contactRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void respondToContact_ShouldRespondToContact_WhenExists() {
        String response = "Test response";
        when(contactRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testContact));
        when(contactRepository.save(any(Contact.class))).thenReturn(testContact);

        Contact result = contactService.respondToContact(1L, response);

        assertNotNull(result);
        assertEquals(response, result.getResponse());
        assertTrue(result.isRead());
        verify(contactRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void respondToContact_ShouldThrowException_WhenNotExists() {
        when(contactRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> contactService.respondToContact(1L, "Test response"));
        verify(contactRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void deleteContact_ShouldSetActiveToFalse_WhenExists() {
        when(contactRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testContact));
        when(contactRepository.save(any(Contact.class))).thenReturn(testContact);

        contactService.deleteContact(1L);

        assertFalse(testContact.isActive());
        verify(contactRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void deleteContact_ShouldThrowException_WhenNotExists() {
        when(contactRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> contactService.deleteContact(1L));
        verify(contactRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(contactRepository, never()).save(any(Contact.class));
    }
} 