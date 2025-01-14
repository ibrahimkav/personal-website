package com.personal.portfolio.repository;

import com.personal.portfolio.entity.User;
import com.personal.portfolio.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private User activeUser;
    private User inactiveUser;

    @BeforeEach
    void setUp() {
        testUser = TestUtil.createTestUser();
        testUser.setActive(true);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        entityManager.persist(testUser);

        activeUser = TestUtil.createTestUser();
        activeUser.setActive(true);
        activeUser.setUsername("activeuser");
        activeUser.setEmail("active@example.com");
        entityManager.persist(activeUser);

        inactiveUser = TestUtil.createTestUser();
        inactiveUser.setActive(false);
        inactiveUser.setUsername("inactiveuser");
        inactiveUser.setEmail("inactive@example.com");
        entityManager.persist(inactiveUser);

        entityManager.flush();
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        List<User> result = userRepository.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void findByUsername_ShouldReturnUser_WhenExists() {
        Optional<User> result = userRepository.findByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals(testUser.getUsername(), result.get().getUsername());
        assertEquals(testUser.getEmail(), result.get().getEmail());
    }

    @Test
    void findByUsername_ShouldReturnEmpty_WhenNotExists() {
        Optional<User> result = userRepository.findByUsername("nonexistent");

        assertFalse(result.isPresent());
    }

    @Test
    void findByEmail_ShouldReturnUser_WhenExists() {
        Optional<User> result = userRepository.findByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals(testUser.getUsername(), result.get().getUsername());
        assertEquals(testUser.getEmail(), result.get().getEmail());
    }

    @Test
    void findByEmail_ShouldReturnEmpty_WhenNotExists() {
        Optional<User> result = userRepository.findByEmail("nonexistent@example.com");

        assertFalse(result.isPresent());
    }

    @Test
    void findByResetPasswordToken_ShouldReturnUser_WhenExists() {
        testUser.setResetPasswordToken("test-reset-token");
        entityManager.persist(testUser);
        entityManager.flush();

        Optional<User> result = userRepository.findByResetPasswordToken("test-reset-token");

        assertTrue(result.isPresent());
        assertEquals(testUser.getUsername(), result.get().getUsername());
    }

    @Test
    void findByResetPasswordToken_ShouldReturnEmpty_WhenNotExists() {
        Optional<User> result = userRepository.findByResetPasswordToken("nonexistent-token");

        assertFalse(result.isPresent());
    }

    @Test
    void findByVerificationToken_ShouldReturnUser_WhenExists() {
        testUser.setVerificationToken("test-verification-token");
        entityManager.persist(testUser);
        entityManager.flush();

        Optional<User> result = userRepository.findByVerificationToken("test-verification-token");

        assertTrue(result.isPresent());
        assertEquals(testUser.getUsername(), result.get().getUsername());
    }

    @Test
    void findByVerificationToken_ShouldReturnEmpty_WhenNotExists() {
        Optional<User> result = userRepository.findByVerificationToken("nonexistent-token");

        assertFalse(result.isPresent());
    }

    @Test
    void existsByUsername_ShouldReturnTrue_WhenExists() {
        boolean result = userRepository.existsByUsername("testuser");

        assertTrue(result);
    }

    @Test
    void existsByUsername_ShouldReturnFalse_WhenNotExists() {
        boolean result = userRepository.existsByUsername("nonexistent");

        assertFalse(result);
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenExists() {
        boolean result = userRepository.existsByEmail("test@example.com");

        assertTrue(result);
    }

    @Test
    void existsByEmail_ShouldReturnFalse_WhenNotExists() {
        boolean result = userRepository.existsByEmail("nonexistent@example.com");

        assertFalse(result);
    }

    @Test
    void save_ShouldPersistUser() {
        User newUser = TestUtil.createTestUser();
        newUser.setUsername("newuser");
        newUser.setEmail("new@example.com");

        User savedUser = userRepository.save(newUser);
        entityManager.flush();

        User foundUser = entityManager.find(User.class, savedUser.getId());
        assertNotNull(foundUser);
        assertEquals(newUser.getUsername(), foundUser.getUsername());
        assertEquals(newUser.getEmail(), foundUser.getEmail());
    }

    @Test
    void update_ShouldUpdateUser() {
        testUser.setUsername("updateduser");
        testUser.setEmail("updated@example.com");

        User updatedUser = userRepository.save(testUser);
        entityManager.flush();

        User foundUser = entityManager.find(User.class, updatedUser.getId());
        assertEquals("updateduser", foundUser.getUsername());
        assertEquals("updated@example.com", foundUser.getEmail());
    }
} 