package com.personal.portfolio.controller;

import com.personal.portfolio.dto.ApiResponse;
import com.personal.portfolio.entity.Project;
import com.personal.portfolio.service.ProjectService;
import com.personal.portfolio.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

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
    void getAllProjects_ShouldReturnProjects() throws Exception {
        when(projectService.getAllProjects()).thenReturn(testProjects);

        MvcResult result = mockMvc.perform(get("/api/projects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
    }

    @Test
    void getFeaturedProjects_ShouldReturnFeaturedProjects() throws Exception {
        when(projectService.getFeaturedProjects()).thenReturn(List.of(testProject));

        MvcResult result = mockMvc.perform(get("/api/projects/featured")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addProject_WithAdminRole_ShouldAddProject() throws Exception {
        when(projectService.addProject(any(Project.class))).thenReturn(testProject);

        MvcResult result = mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(testProject)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("Project added successfully", response.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProject_WithAdminRole_ShouldUpdateProject() throws Exception {
        when(projectService.updateProject(anyLong(), any(Project.class)))
                .thenReturn(testProject);

        MvcResult result = mockMvc.perform(put("/api/projects/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(testProject)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("Project updated successfully", response.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteProject_WithAdminRole_ShouldDeleteProject() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/projects/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("Project deleted successfully", response.getMessage());
    }

    @Test
    @WithMockUser(roles = "USER")
    void addProject_WithUserRole_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(testProject)))
                .andExpect(status().isForbidden());
    }

    @Test
    void addProject_WithoutAuth_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(testProject)))
                .andExpect(status().isUnauthorized());
    }
} 