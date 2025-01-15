package com.personal.portfolio.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    @Builder.Default
    private String type = "Bearer";
    private String accessToken;
    private String username;
    private String email;
    private String role;
} 