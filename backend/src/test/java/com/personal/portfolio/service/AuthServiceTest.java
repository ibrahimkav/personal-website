package com.personal.portfolio.service;

import com.personal.portfolio.dto.auth.LoginRequest;
import com.personal.portfolio.dto.auth.SignupRequest;
import com.personal.portfolio.dto.auth.TokenResponse;
import com.personal.portfolio.entity.User;
import com.personal.portfolio.exception.AuthenticationException;
import com.personal.portfolio.exception.EntityNotFoundException;
import com.personal.portfolio.repository.UserRepository;
import com.personal.portfolio.security.JwtTokenProvider;
import com.personal.portfolio.service.impl.AuthServiceImpl;
import com.personal.portfolio.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    private User testUser;
    private LoginRequest loginRequest;
    private SignupRequest signupRequest;
    private String testToken;

    @BeforeEach
    void setUp() {
        testUser = TestUtil.createTestUser();
        testToken = "test.jwt.token";

        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        signupRequest = new SignupRequest();
        signupRequest.setUsername("newuser");
        signupRequest.setPassword("password123");
        signupRequest.setEmail("newuser@example.com");
        signupRequest.setFullName("New User");
    }

    @Test
    void login_WithValidCredentials_ShouldReturnToken() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        when(userRepository.findByUsernameAndActiveTrue(anyString()))
                .thenReturn(Optional.of(testUser));
        when(jwtTokenProvider.generateToken(any(User.class))).thenReturn(testToken);

        TokenResponse result = authService.login(loginRequest);

        assertNotNull(result);
        assertEquals(testToken, result.getAccessToken());
        assertEquals("Bearer", result.getTokenType());
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1))
                .findByUsernameAndActiveTrue(loginRequest.getUsername());
        verify(jwtTokenProvider, times(1)).generateToken(testUser);
    }

    @Test
    void login_WithInvalidCredentials_ShouldThrowException() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(AuthenticationException.class, () -> authService.login(loginRequest));
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).findByUsernameAndActiveTrue(anyString());
        verify(jwtTokenProvider, never()).generateToken(any(User.class));
    }

    @Test
    void signup_WithValidData_ShouldCreateUserAndReturnToken() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(jwtTokenProvider.generateToken(any(User.class))).thenReturn(testToken);

        TokenResponse result = authService.signup(signupRequest);

        assertNotNull(result);
        assertEquals(testToken, result.getAccessToken());
        assertEquals("Bearer", result.getTokenType());
        verify(userRepository, times(1)).existsByUsername(signupRequest.getUsername());
        verify(userRepository, times(1)).existsByEmail(signupRequest.getEmail());
        verify(passwordEncoder, times(1)).encode(signupRequest.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtTokenProvider, times(1)).generateToken(any(User.class));
    }

    @Test
    void signup_WithExistingUsername_ShouldThrowException() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        assertThrows(AuthenticationException.class, () -> authService.signup(signupRequest));
        verify(userRepository, times(1)).existsByUsername(signupRequest.getUsername());
        verify(userRepository, never()).existsByEmail(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(jwtTokenProvider, never()).generateToken(any(User.class));
    }

    @Test
    void signup_WithExistingEmail_ShouldThrowException() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(AuthenticationException.class, () -> authService.signup(signupRequest));
        verify(userRepository, times(1)).existsByUsername(signupRequest.getUsername());
        verify(userRepository, times(1)).existsByEmail(signupRequest.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(jwtTokenProvider, never()).generateToken(any(User.class));
    }
} 