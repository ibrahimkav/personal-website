package com.personal.portfolio.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest {

    @Test
    public void testAuthentication() {
        // Create password encoder
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String password = "admin123";
        String dbHash = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";
        
        // Debug: Generate a new hash for comparison
        String newHash = passwordEncoder.encode(password);
        System.out.println("Generated hash: " + newHash);
        System.out.println("Database hash: " + dbHash);
        System.out.println("Matches: " + passwordEncoder.matches(password, dbHash));

        // Create user details service
        UserDetails userDetails = User.builder()
                .username("admin")
                .password(dbHash)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .build();

        // Create authentication provider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(username -> userDetails);

        // Create authentication token
        Authentication authentication = new UsernamePasswordAuthenticationToken("admin", password);

        try {
            // Attempt authentication
            Authentication result = provider.authenticate(authentication);
            assertTrue(result.isAuthenticated(), "Authentication should succeed");
            assertEquals("admin", result.getName(), "Username should match");
        } catch (BadCredentialsException e) {
            fail("Authentication should not fail: " + e.getMessage());
        }
    }
} 