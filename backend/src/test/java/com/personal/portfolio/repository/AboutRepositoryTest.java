package com.personal.portfolio.repository;

import com.personal.portfolio.entity.About;
import com.personal.portfolio.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AboutRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AboutRepository aboutRepository;

    private About testAbout;

    @BeforeEach
    void setUp() {
        testAbout = TestUtil.createTestAbout();
        testAbout.setActive(true);
        entityManager.persist(testAbout);
        entityManager.flush();
    }

    @Test
    void findFirstByActiveTrue_ShouldReturnAbout_WhenExists() {
        Optional<About> result = aboutRepository.findFirstByActiveTrue();

        assertTrue(result.isPresent());
        assertEquals(testAbout.getFullName(), result.get().getFullName());
        assertEquals(testAbout.getTitle(), result.get().getTitle());
    }

    @Test
    void findFirstByActiveTrue_ShouldReturnEmpty_WhenNotExists() {
        testAbout.setActive(false);
        entityManager.persist(testAbout);
        entityManager.flush();

        Optional<About> result = aboutRepository.findFirstByActiveTrue();

        assertFalse(result.isPresent());
    }

    @Test
    void save_ShouldPersistAbout() {
        About newAbout = TestUtil.createTestAbout();
        newAbout.setFullName("New Name");
        newAbout.setTitle("New Title");

        About savedAbout = aboutRepository.save(newAbout);
        entityManager.flush();

        About foundAbout = entityManager.find(About.class, savedAbout.getId());
        assertNotNull(foundAbout);
        assertEquals(newAbout.getFullName(), foundAbout.getFullName());
        assertEquals(newAbout.getTitle(), foundAbout.getTitle());
    }

    @Test
    void update_ShouldUpdateAbout() {
        testAbout.setFullName("Updated Name");
        testAbout.setTitle("Updated Title");

        About updatedAbout = aboutRepository.save(testAbout);
        entityManager.flush();

        About foundAbout = entityManager.find(About.class, updatedAbout.getId());
        assertEquals("Updated Name", foundAbout.getFullName());
        assertEquals("Updated Title", foundAbout.getTitle());
    }
} 