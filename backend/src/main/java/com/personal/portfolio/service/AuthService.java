package com.personal.portfolio.service;

import com.personal.portfolio.dto.auth.LoginRequest;
import com.personal.portfolio.dto.auth.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
} 