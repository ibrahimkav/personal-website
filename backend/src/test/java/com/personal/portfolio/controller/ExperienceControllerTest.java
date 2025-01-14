package com.personal.portfolio.controller;

import com.personal.portfolio.dto.ApiResponse;
import com.personal.portfolio.entity.Experience;
import com.personal.portfolio.entity.Experience.ExperienceType;
import com.personal.portfolio.service.ExperienceService;
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

@WebMvcTest(ExperienceController.class)
class ExperienceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExperienceService experienceService;

    private Experience testExperience;
    private List<Experience> testExperiences;

    @BeforeEach
    void setUp() {
        testExperience = TestUtil.createTestExperience();
        
        Experience workExperience = TestUtil.createTestExperience();
        workExperience.setType(ExperienceType.WORK);
        
        Experience educationExperience = TestUtil.createTestExperience();
        educationExperience.setType(ExperienceType.EDUCATION);
        
        testExperiences = Arrays.asList(workExperience, educationExperience);
    }

    @Test
    void getAllExperiences_ShouldReturnExperiences() throws Exception {
        when(experienceService.getAllExperiences()).thenReturn(testExperiences);

        MvcResult result = mockMvc.perform(get("/api/experiences")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
    }

    @Test
    void getExperiencesByType_ShouldReturnFilteredExperiences() throws Exception {
        when(experienceService.getExperiencesByType(ExperienceType.WORK))
                .thenReturn(List.of(testExperience));

        MvcResult result = mockMvc.perform(get("/api/experiences/type/WORK")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addExperience_WithAdminRole_ShouldAddExperience() throws Exception {
        when(experienceService.addExperience(any(Experience.class))).thenReturn(testExperience);

        MvcResult result = mockMvc.perform(post("/api/experiences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(testExperience)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("Experience added successfully", response.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateExperience_WithAdminRole_ShouldUpdateExperience() throws Exception {
        when(experienceService.updateExperience(anyLong(), any(Experience.class)))
                .thenReturn(testExperience);

        MvcResult result = mockMvc.perform(put("/api/experiences/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(testExperience)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("Experience updated successfully", response.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteExperience_WithAdminRole_ShouldDeleteExperience() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/experiences/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<?> response = TestUtil.parseResponse(result, ApiResponse.class);
        assertTrue(response.isSuccess());
        assertEquals("Experience deleted successfully", response.getMessage());
    }

    @Test
    @WithMockUser(roles = "USER")
    void addExperience_WithUserRole_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(post("/api/experiences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(testExperience)))
                .andExpect(status().isForbidden());
    }

    @Test
    void addExperience_WithoutAuth_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(post("/api/experiences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(testExperience)))
                .andExpect(status().isUnauthorized());
    }
} 