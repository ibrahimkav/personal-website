package com.personal.portfolio.security;

import com.personal.portfolio.entity.User;
import com.personal.portfolio.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private final String SECRET = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private final long EXPIRATION = 86400000; // 1 day

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(SECRET, EXPIRATION);
    }

    @Test
    void generateToken_ShouldCreateValidToken() {
        // Arrange
        User user = TestUtil.createTestUser();

        // Act
        String token = jwtTokenProvider.generateToken(user);

        // Assert
        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
        assertEquals(user.getUsername(), jwtTokenProvider.getUsernameFromToken(token));
    }

    @Test
    void validateToken_WithInvalidToken_ShouldReturnFalse() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act & Assert
        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }

    @Test
    void getAuthenticationFromToken_WithValidToken_ShouldReturnAuthentication() {
        // Arrange
        User user = TestUtil.createTestUser();
        String token = jwtTokenProvider.generateToken(user);

        // Act
        Authentication authentication = jwtTokenProvider.getAuthenticationFromToken(token);

        // Assert
        assertNotNull(authentication);
        assertEquals(user.getUsername(), authentication.getName());
        assertTrue(authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + user.getRole().name())));
    }

    @Test
    void getAuthenticationFromToken_WithInvalidToken_ShouldReturnNull() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act
        Authentication authentication = jwtTokenProvider.getAuthenticationFromToken(invalidToken);

        // Assert
        assertNull(authentication);
    }
} 