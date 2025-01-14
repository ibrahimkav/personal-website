package com.personal.portfolio.controller;

import com.personal.portfolio.dto.ApiResponse;
import com.personal.portfolio.entity.User;
import com.personal.portfolio.service.UserService;
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

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User testUser;
    private List<User> testUsers;

    @BeforeEach
    void setUp() {
        testUser = TestUtil.createTestUser();
        testUsers = Arrays.asList(testUser, TestUtil.createTestUser());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers_WithAdminRole_ShouldReturnUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(testUsers);

        MvcResult result = mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getUserById_WithAdminRole_ShouldReturnUser() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(testUser);

        MvcResult result = mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_WithAdminRole_ShouldUpdateUser() throws Exception {
        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(testUser);

        MvcResult result = mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(testUser)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("User updated successfully", response.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_WithAdminRole_ShouldDeleteUser() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("User deleted successfully", response.getMessage());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllUsers_WithUserRole_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllUsers_WithoutAuth_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void verifyEmail_WithAdminRole_ShouldVerifyUserEmail() throws Exception {
        when(userService.verifyEmail(anyLong())).thenReturn(testUser);

        MvcResult result = mockMvc.perform(put("/api/users/1/verify-email")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("User email verified successfully", response.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void resetPassword_WithAdminRole_ShouldResetUserPassword() throws Exception {
        when(userService.resetPassword(anyLong())).thenReturn(testUser);

        MvcResult result = mockMvc.perform(put("/api/users/1/reset-password")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("User password reset successfully", response.getMessage());
    }
} 