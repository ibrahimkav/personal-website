package com.personal.portfolio.controller;

import com.personal.portfolio.dto.ApiResponse;
import com.personal.portfolio.entity.Contact;
import com.personal.portfolio.service.ContactService;
import com.personal.portfolio.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    private Contact testContact;
    private List<Contact> testContacts;

    @BeforeEach
    void setUp() {
        testContact = TestUtil.createTestContact();
        
        Contact readContact = TestUtil.createTestContact();
        readContact.setRead(true);
        
        Contact unreadContact = TestUtil.createTestContact();
        unreadContact.setRead(false);
        
        testContacts = Arrays.asList(readContact, unreadContact);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllContacts_WithAdminRole_ShouldReturnContacts() throws Exception {
        when(contactService.getAllContacts()).thenReturn(testContacts);

        MvcResult result = mockMvc.perform(get("/api/contacts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getUnreadContacts_WithAdminRole_ShouldReturnUnreadContacts() throws Exception {
        when(contactService.getUnreadContacts()).thenReturn(List.of(testContact));

        MvcResult result = mockMvc.perform(get("/api/contacts/unread")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
    }

    @Test
    void submitContact_ShouldSubmitContactMessage() throws Exception {
        when(contactService.submitContact(any(Contact.class))).thenReturn(testContact);

        MvcResult result = mockMvc.perform(post("/api/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(testContact)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("Contact message submitted successfully", response.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void markAsRead_WithAdminRole_ShouldMarkContactAsRead() throws Exception {
        when(contactService.markAsRead(anyLong())).thenReturn(testContact);

        MvcResult result = mockMvc.perform(put("/api/contacts/1/read")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("Contact marked as read successfully", response.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void respondToContact_WithAdminRole_ShouldRespondToContact() throws Exception {
        when(contactService.respondToContact(anyLong(), any(String.class))).thenReturn(testContact);

        MvcResult result = mockMvc.perform(put("/api/contacts/1/respond")
                .contentType(MediaType.APPLICATION_JSON)
                .content("Test response message"))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("Response sent successfully", response.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteContact_WithAdminRole_ShouldDeleteContact() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/contacts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("Contact deleted successfully", response.getMessage());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllContacts_WithUserRole_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/contacts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllContacts_WithoutAuth_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/contacts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
} 