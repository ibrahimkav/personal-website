package com.personal.portfolio.service;

import com.personal.portfolio.entity.Project;
import com.personal.portfolio.exception.EntityNotFoundException;
import com.personal.portfolio.repository.ProjectRepository;
import com.personal.portfolio.service.impl.ProjectServiceImpl;
import com.personal.portfolio.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project testProject;
    private List<Project> testProjects;

    @BeforeEach
    void setUp() {
        testProject = TestUtil.createTestProject();
        
        Project featuredProject = TestUtil.createTestProject();
        featuredProject.setFeatured(true);
        
        Project nonFeaturedProject = TestUtil.createTestProject();
        nonFeaturedProject.setFeatured(false);
        
        testProjects = Arrays.asList(featuredProject, nonFeaturedProject);
    }

    @Test
    void getAllProjects_ShouldReturnProjects() {
        when(projectRepository.findAllByActiveTrue()).thenReturn(testProjects);

        List<Project> result = projectService.getAllProjects();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectRepository, times(1)).findAllByActiveTrue();
    }

    @Test
    void getFeaturedProjects_ShouldReturnFeaturedProjects() {
        when(projectRepository.findAllByFeaturedTrueAndActiveTrue())
                .thenReturn(List.of(testProject));

        List<Project> result = projectService.getFeaturedProjects();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isFeatured());
        verify(projectRepository, times(1)).findAllByFeaturedTrueAndActiveTrue();
    }

    @Test
    void getProjectById_ShouldReturnProject_WhenExists() {
        when(projectRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testProject));

        Project result = projectService.getProjectById(1L);

        assertNotNull(result);
        assertEquals(testProject.getName(), result.getName());
        assertEquals(testProject.getDescription(), result.getDescription());
        verify(projectRepository, times(1)).findByIdAndActiveTrue(1L);
    }

    @Test
    void getProjectById_ShouldThrowException_WhenNotExists() {
        when(projectRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> projectService.getProjectById(1L));
        verify(projectRepository, times(1)).findByIdAndActiveTrue(1L);
    }

    @Test
    void addProject_ShouldAddAndReturnProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);

        Project result = projectService.addProject(testProject);

        assertNotNull(result);
        assertEquals(testProject.getName(), result.getName());
        assertEquals(testProject.getDescription(), result.getDescription());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void updateProject_ShouldUpdateAndReturnProject_WhenExists() {
        Project updatedProject = TestUtil.createTestProject();
        updatedProject.setName("Updated Name");
        updatedProject.setDescription("Updated Description");

        when(projectRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testProject));
        when(projectRepository.save(any(Project.class))).thenReturn(updatedProject);

        Project result = projectService.updateProject(1L, updatedProject);

        assertNotNull(result);
        assertEquals(updatedProject.getName(), result.getName());
        assertEquals(updatedProject.getDescription(), result.getDescription());
        verify(projectRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void updateProject_ShouldThrowException_WhenNotExists() {
        when(projectRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> projectService.updateProject(1L, testProject));
        verify(projectRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void deleteProject_ShouldSetActiveToFalse_WhenExists() {
        when(projectRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testProject));
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);

        projectService.deleteProject(1L);

        assertFalse(testProject.isActive());
        verify(projectRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void deleteProject_ShouldThrowException_WhenNotExists() {
        when(projectRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> projectService.deleteProject(1L));
        verify(projectRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(projectRepository, never()).save(any(Project.class));
    }
} 