package com.personal.portfolio.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncoderTest {

    @Test
    public void testPasswordEncoding() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String password = "admin123";
        
        // Test with the hash from database
        String dbHash = "$2a$10$BjD38NnWr6IwbqkJPzoGBe0FtTujkTBnwfIAjUOjlvlFNlVK2j9.u";
        
        // Test direct matching
        boolean matchesDb = encoder.matches(password, dbHash);
        System.out.println("Direct matching result: " + matchesDb);
        
        // Generate a new hash and compare
        String newHash = encoder.encode(password);
        System.out.println("Generated hash: " + newHash);
        boolean matchesNew = encoder.matches(password, newHash);
        System.out.println("New hash matching result: " + matchesNew);
        
        // Compare raw hashes
        System.out.println("DB Hash   : " + dbHash);
        System.out.println("Input Hash: " + newHash);
        
        assertTrue(matchesDb, "Password should match the database hash");
        assertTrue(matchesNew, "Password should match the newly generated hash");
    }
} 