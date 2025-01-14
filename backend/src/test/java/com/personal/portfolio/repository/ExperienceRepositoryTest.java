package com.personal.portfolio.repository;

import com.personal.portfolio.entity.Experience;
import com.personal.portfolio.entity.Experience.ExperienceType;
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
class ExperienceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExperienceRepository experienceRepository;

    private Experience testExperience;
    private Experience workExperience;
    private Experience educationExperience;

    @BeforeEach
    void setUp() {
        testExperience = TestUtil.createTestExperience();
        testExperience.setActive(true);
        entityManager.persist(testExperience);

        workExperience = TestUtil.createTestExperience();
        workExperience.setType(ExperienceType.WORK);
        workExperience.setActive(true);
        entityManager.persist(workExperience);

        educationExperience = TestUtil.createTestExperience();
        educationExperience.setType(ExperienceType.EDUCATION);
        educationExperience.setActive(true);
        entityManager.persist(educationExperience);

        entityManager.flush();
    }

    @Test
    void findByActiveTrueOrderByStartDateDesc_ShouldReturnAllActiveExperiences() {
        List<Experience> result = experienceRepository.findByActiveTrueOrderByStartDateDesc();

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void findByTypeAndActiveTrueOrderByStartDateDesc_ShouldReturnActiveExperiencesOfType() {
        List<Experience> result = experienceRepository.findByTypeAndActiveTrueOrderByStartDateDesc(ExperienceType.WORK);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ExperienceType.WORK, result.get(0).getType());
    }

    @Test
    void findByIdAndActiveTrue_ShouldReturnExperience_WhenExists() {
        Optional<Experience> result = experienceRepository.findByIdAndActiveTrue(testExperience.getId());

        assertTrue(result.isPresent());
        assertEquals(testExperience.getTitle(), result.get().getTitle());
        assertEquals(testExperience.getOrganization(), result.get().getOrganization());
    }

    @Test
    void findByIdAndActiveTrue_ShouldReturnEmpty_WhenNotExists() {
        testExperience.setActive(false);
        entityManager.persist(testExperience);
        entityManager.flush();

        Optional<Experience> result = experienceRepository.findByIdAndActiveTrue(testExperience.getId());

        assertFalse(result.isPresent());
    }

    @Test
    void save_ShouldPersistExperience() {
        Experience newExperience = TestUtil.createTestExperience();
        newExperience.setTitle("New Title");
        newExperience.setOrganization("New Organization");

        Experience savedExperience = experienceRepository.save(newExperience);
        entityManager.flush();

        Experience foundExperience = entityManager.find(Experience.class, savedExperience.getId());
        assertNotNull(foundExperience);
        assertEquals(newExperience.getTitle(), foundExperience.getTitle());
        assertEquals(newExperience.getOrganization(), foundExperience.getOrganization());
    }

    @Test
    void update_ShouldUpdateExperience() {
        testExperience.setTitle("Updated Title");
        testExperience.setOrganization("Updated Organization");

        Experience updatedExperience = experienceRepository.save(testExperience);
        entityManager.flush();

        Experience foundExperience = entityManager.find(Experience.class, updatedExperience.getId());
        assertEquals("Updated Title", foundExperience.getTitle());
        assertEquals("Updated Organization", foundExperience.getOrganization());
    }
} 