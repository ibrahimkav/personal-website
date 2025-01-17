package com.personal.portfolio.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String tokenType;
    private Long id;
    private String username;
    private String email;
    private String role;
} 