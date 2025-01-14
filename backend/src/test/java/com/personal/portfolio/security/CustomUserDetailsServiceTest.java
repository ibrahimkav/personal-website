package com.personal.portfolio.security;

import com.personal.portfolio.entity.User;
import com.personal.portfolio.repository.UserRepository;
import com.personal.portfolio.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = TestUtil.createTestUser();
    }

    @Test
    void loadUserByUsername_WithValidUsername_ShouldReturnUserDetails() {
        when(userRepository.findByUsernameAndActiveTrue(anyString()))
                .thenReturn(Optional.of(testUser));

        UserDetails userDetails = userDetailsService.loadUserByUsername(testUser.getUsername());

        assertNotNull(userDetails);
        assertEquals(testUser.getUsername(), userDetails.getUsername());
        verify(userRepository, times(1))
                .findByUsernameAndActiveTrue(testUser.getUsername());
    }

    @Test
    void loadUserByUsername_WithInvalidUsername_ShouldThrowException() {
        String invalidUsername = "nonexistent";
        when(userRepository.findByUsernameAndActiveTrue(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(invalidUsername));
        verify(userRepository, times(1))
                .findByUsernameAndActiveTrue(invalidUsername);
    }

    @Test
    void loadUserByUsername_WithNullUsername_ShouldThrowException() {
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(null));
        verify(userRepository, never()).findByUsernameAndActiveTrue(anyString());
    }

    @Test
    void loadUserByUsername_WithEmptyUsername_ShouldThrowException() {
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(""));
        verify(userRepository, never()).findByUsernameAndActiveTrue(anyString());
    }
} 