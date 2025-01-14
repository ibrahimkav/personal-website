package com.personal.portfolio.repository;

import com.personal.portfolio.entity.Project;
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
class ProjectRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProjectRepository projectRepository;

    private Project testProject;
    private Project featuredProject;
    private Project nonFeaturedProject;

    @BeforeEach
    void setUp() {
        testProject = TestUtil.createTestProject();
        testProject.setActive(true);
        entityManager.persist(testProject);

        featuredProject = TestUtil.createTestProject();
        featuredProject.setFeatured(true);
        featuredProject.setActive(true);
        entityManager.persist(featuredProject);

        nonFeaturedProject = TestUtil.createTestProject();
        nonFeaturedProject.setFeatured(false);
        nonFeaturedProject.setActive(true);
        entityManager.persist(nonFeaturedProject);

        entityManager.flush();
    }

    @Test
    void findByActiveTrueOrderByOrderAsc_ShouldReturnAllActiveProjects() {
        List<Project> result = projectRepository.findByActiveTrueOrderByOrderAsc();

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void findByFeaturedTrueAndActiveTrueOrderByOrderAsc_ShouldReturnActiveFeaturedProjects() {
        List<Project> result = projectRepository.findByFeaturedTrueAndActiveTrueOrderByOrderAsc();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getFeatured());
    }

    @Test
    void findByIdAndActiveTrue_ShouldReturnProject_WhenExists() {
        Optional<Project> result = projectRepository.findByIdAndActiveTrue(testProject.getId());

        assertTrue(result.isPresent());
        assertEquals(testProject.getName(), result.get().getName());
        assertEquals(testProject.getDescription(), result.get().getDescription());
    }

    @Test
    void findByIdAndActiveTrue_ShouldReturnEmpty_WhenNotExists() {
        testProject.setActive(false);
        entityManager.persist(testProject);
        entityManager.flush();

        Optional<Project> result = projectRepository.findByIdAndActiveTrue(testProject.getId());

        assertFalse(result.isPresent());
    }

    @Test
    void save_ShouldPersistProject() {
        Project newProject = TestUtil.createTestProject();
        newProject.setName("New Project");
        newProject.setDescription("New Description");

        Project savedProject = projectRepository.save(newProject);
        entityManager.flush();

        Project foundProject = entityManager.find(Project.class, savedProject.getId());
        assertNotNull(foundProject);
        assertEquals(newProject.getName(), foundProject.getName());
        assertEquals(newProject.getDescription(), foundProject.getDescription());
    }

    @Test
    void update_ShouldUpdateProject() {
        testProject.setName("Updated Project");
        testProject.setDescription("Updated Description");

        Project updatedProject = projectRepository.save(testProject);
        entityManager.flush();

        Project foundProject = entityManager.find(Project.class, updatedProject.getId());
        assertEquals("Updated Project", foundProject.getName());
        assertEquals("Updated Description", foundProject.getDescription());
    }
} 