package com.personal.portfolio.service;

import com.personal.portfolio.entity.User;

public interface UserService {
    User getUserById(Long id);
    User getUserByUsername(String username);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    void changePassword(Long id, String oldPassword, String newPassword);
    void resetPassword(String email);
    void verifyEmail(String token);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 