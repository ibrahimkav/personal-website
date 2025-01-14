package com.personal.portfolio.service;

import com.personal.portfolio.entity.User;
import com.personal.portfolio.exception.EntityNotFoundException;
import com.personal.portfolio.repository.UserRepository;
import com.personal.portfolio.service.impl.UserServiceImpl;
import com.personal.portfolio.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private List<User> testUsers;

    @BeforeEach
    void setUp() {
        testUser = TestUtil.createTestUser();
        testUsers = Arrays.asList(testUser, TestUtil.createTestUser());
    }

    @Test
    void getAllUsers_ShouldReturnUsers() {
        when(userRepository.findAllByActiveTrue()).thenReturn(testUsers);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAllByActiveTrue();
    }

    @Test
    void getUserById_ShouldReturnUser_WhenExists() {
        when(userRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testUser));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByIdAndActiveTrue(1L);
    }

    @Test
    void getUserById_ShouldThrowException_WhenNotExists() {
        when(userRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));
        verify(userRepository, times(1)).findByIdAndActiveTrue(1L);
    }

    @Test
    void updateUser_ShouldUpdateAndReturnUser_WhenExists() {
        User updatedUser = TestUtil.createTestUser();
        updatedUser.setFullName("Updated Name");
        updatedUser.setEmail("updated@example.com");

        when(userRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getFullName(), result.getFullName());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_ShouldThrowException_WhenNotExists() {
        when(userRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.updateUser(1L, testUser));
        verify(userRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void verifyEmail_ShouldVerifyAndReturnUser_WhenExists() {
        when(userRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.verifyEmail(1L);

        assertNotNull(result);
        assertTrue(result.isEmailVerified());
        verify(userRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void verifyEmail_ShouldThrowException_WhenNotExists() {
        when(userRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.verifyEmail(1L));
        verify(userRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void resetPassword_ShouldResetAndReturnUser_WhenExists() {
        String newPassword = "newPassword123";
        when(userRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.resetPassword(1L);

        assertNotNull(result);
        verify(userRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void resetPassword_ShouldThrowException_WhenNotExists() {
        when(userRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.resetPassword(1L));
        verify(userRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_ShouldSetActiveToFalse_WhenExists() {
        when(userRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.deleteUser(1L);

        assertFalse(testUser.isActive());
        verify(userRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUser_ShouldThrowException_WhenNotExists() {
        when(userRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(userRepository, never()).save(any(User.class));
    }
} 