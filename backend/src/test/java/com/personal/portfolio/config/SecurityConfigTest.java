package com.personal.portfolio.config;

import com.personal.portfolio.security.CustomUserDetailsService;
import com.personal.portfolio.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void passwordEncoder_ShouldReturnBCryptPasswordEncoder() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder.matches("password",
                passwordEncoder.encode("password")));
    }

    @Test
    void authenticationProvider_ShouldReturnDaoAuthenticationProvider() {
        AuthenticationProvider authenticationProvider = securityConfig.authenticationProvider();

        assertNotNull(authenticationProvider);
    }

    @Test
    void authenticationManager_ShouldReturnAuthenticationManager() throws Exception {
        AuthenticationManager authenticationManager = securityConfig.authenticationManager(
                securityConfig.authenticationConfiguration);

        assertNotNull(authenticationManager);
    }

    @Test
    void jwtAuthenticationFilter_ShouldReturnJwtAuthenticationFilter() {
        JwtAuthenticationFilter filter = securityConfig.jwtAuthenticationFilter();

        assertNotNull(filter);
    }

    @Test
    void corsConfigurationSource_ShouldReturnCorsConfigurationSource() {
        CorsConfigurationSource corsConfigurationSource = securityConfig.corsConfigurationSource();

        assertNotNull(corsConfigurationSource);
        CorsConfiguration corsConfiguration = corsConfigurationSource.getCorsConfiguration(null);
        assertNotNull(corsConfiguration);
        assertTrue(corsConfiguration.getAllowedOrigins().contains("*"));
        assertTrue(corsConfiguration.getAllowedMethods().containsAll(
                java.util.Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")));
        assertTrue(corsConfiguration.getAllowedHeaders().contains("*"));
    }

    @Test
    void filterChain_ShouldConfigureSecurityFilterChain() throws Exception {
        SecurityFilterChain filterChain = securityConfig.filterChain(
                securityConfig.getHttpSecurity());

        assertNotNull(filterChain);
    }

    @Test
    void getHttpSecurity_ShouldReturnHttpSecurity() {
        HttpSecurity httpSecurity = securityConfig.getHttpSecurity();

        assertNotNull(httpSecurity);
    }
} 