package com.personal.portfolio.security;

import com.personal.portfolio.entity.User;
import com.personal.portfolio.util.TestUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private User testUser;
    private String jwtSecret;
    private long jwtExpirationInMs;

    @BeforeEach
    void setUp() {
        jwtSecret = "testSecret";
        jwtExpirationInMs = 3600000; // 1 hour
        jwtTokenProvider = new JwtTokenProvider(jwtSecret, jwtExpirationInMs);
        testUser = TestUtil.createTestUser();
    }

    @Test
    void generateToken_ShouldGenerateValidToken() {
        String token = jwtTokenProvider.generateToken(testUser);

        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));

        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        assertEquals(testUser.getUsername(), claims.getSubject());
        assertTrue(claims.getExpiration().after(new Date()));
    }

    @Test
    void validateToken_WithValidToken_ShouldReturnTrue() {
        String token = jwtTokenProvider.generateToken(testUser);

        boolean isValid = jwtTokenProvider.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    void validateToken_WithInvalidToken_ShouldReturnFalse() {
        String invalidToken = "invalid.token.string";

        boolean isValid = jwtTokenProvider.validateToken(invalidToken);

        assertFalse(isValid);
    }

    @Test
    void validateToken_WithExpiredToken_ShouldReturnFalse() {
        jwtExpirationInMs = 0; // Immediate expiration
        jwtTokenProvider = new JwtTokenProvider(jwtSecret, jwtExpirationInMs);
        String token = jwtTokenProvider.generateToken(testUser);

        boolean isValid = jwtTokenProvider.validateToken(token);

        assertFalse(isValid);
    }

    @Test
    void getUsernameFromToken_ShouldReturnUsername() {
        String token = jwtTokenProvider.generateToken(testUser);

        String username = jwtTokenProvider.getUsernameFromToken(token);

        assertEquals(testUser.getUsername(), username);
    }

    @Test
    void getAuthenticationFromToken_ShouldReturnAuthentication() {
        String token = jwtTokenProvider.generateToken(testUser);

        Authentication authentication = jwtTokenProvider.getAuthenticationFromToken(token);

        assertNotNull(authentication);
        assertEquals(testUser.getUsername(), authentication.getName());
        assertTrue(authentication.isAuthenticated());
    }

    @Test
    void getAuthenticationFromToken_WithInvalidToken_ShouldReturnNull() {
        String invalidToken = "invalid.token.string";

        Authentication authentication = jwtTokenProvider.getAuthenticationFromToken(invalidToken);

        assertNull(authentication);
    }
} 