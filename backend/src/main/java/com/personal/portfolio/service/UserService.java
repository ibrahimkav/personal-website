package com.personal.portfolio.service;

import com.personal.portfolio.entity.User;
import com.personal.portfolio.service.base.BaseService;

public interface UserService extends BaseService<User, Long> {
    User register(User user);
    
    User findByUsername(String username);
    
    User findByEmail(String email);
    
    void changePassword(Long userId, String oldPassword, String newPassword);
    
    void resetPassword(String email);
    
    void confirmResetPassword(String token, String newPassword);
    
    void verifyEmail(String token);
    
    void sendVerificationEmail(User user);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
} 