package com.personal.portfolio.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "linkedin.api")
public class LinkedInConfig {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String scope = "r_liteprofile r_emailaddress r_basicprofile";
} 