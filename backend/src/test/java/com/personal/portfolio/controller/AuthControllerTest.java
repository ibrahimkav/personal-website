package com.personal.portfolio.controller;

import com.personal.portfolio.dto.ApiResponse;
import com.personal.portfolio.dto.auth.LoginRequest;
import com.personal.portfolio.dto.auth.SignupRequest;
import com.personal.portfolio.dto.auth.TokenResponse;
import com.personal.portfolio.service.AuthService;
import com.personal.portfolio.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    private LoginRequest loginRequest;
    private SignupRequest signupRequest;
    private TokenResponse tokenResponse;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        signupRequest = new SignupRequest();
        signupRequest.setUsername("newuser");
        signupRequest.setPassword("password123");
        signupRequest.setEmail("newuser@example.com");
        signupRequest.setFullName("New User");

        tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken("test.jwt.token");
        tokenResponse.setTokenType("Bearer");
    }

    @Test
    void login_WithValidCredentials_ShouldReturnToken() throws Exception {
        when(authService.login(any(LoginRequest.class))).thenReturn(tokenResponse);

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("Login successful", response.getMessage());
    }

    @Test
    void signup_WithValidData_ShouldCreateUser() throws Exception {
        when(authService.signup(any(SignupRequest.class))).thenReturn(tokenResponse);

        MvcResult result = mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(signupRequest)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("User registered successfully", response.getMessage());
    }

    @Test
    void login_WithInvalidCredentials_ShouldReturnUnauthorized() throws Exception {
        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void signup_WithExistingUsername_ShouldReturnBadRequest() throws Exception {
        when(authService.signup(any(SignupRequest.class)))
                .thenThrow(new RuntimeException("Username is already taken"));

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(signupRequest)))
                .andExpect(status().isBadRequest());
    }
} 