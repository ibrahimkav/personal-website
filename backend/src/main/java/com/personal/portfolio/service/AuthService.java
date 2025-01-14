package com.personal.portfolio.service;

import com.personal.portfolio.dto.auth.LoginRequest;
import com.personal.portfolio.dto.auth.SignupRequest;
import com.personal.portfolio.dto.auth.TokenResponse;

public interface AuthService {
    TokenResponse login(LoginRequest loginRequest);
    
    TokenResponse signup(SignupRequest signupRequest);
    
    void verifyEmail(String token);
    
    void resetPassword(String email);
    
    void confirmResetPassword(String token, String newPassword);
    
    void changePassword(String oldPassword, String newPassword);
} 