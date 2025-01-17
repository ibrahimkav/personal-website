package com.personal.portfolio.service;

import com.personal.portfolio.dto.auth.LoginRequest;
import com.personal.portfolio.dto.auth.LoginResponse;
import com.personal.portfolio.dto.auth.RegisterRequest;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse register(RegisterRequest registerRequest);
} 