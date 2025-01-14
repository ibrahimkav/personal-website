package com.personal.portfolio.controller;

import com.personal.portfolio.dto.ApiResponse;
import com.personal.portfolio.dto.about.AboutResponse;
import com.personal.portfolio.entity.About;
import com.personal.portfolio.service.AboutService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AboutController.class)
class AboutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AboutService aboutService;

    private About testAbout;

    @BeforeEach
    void setUp() {
        testAbout = TestUtil.createTestAbout();
    }

    @Test
    void getAbout_ShouldReturnAboutInfo() throws Exception {
        when(aboutService.getActiveAbout()).thenReturn(testAbout);

        MvcResult result = mockMvc.perform(get("/api/about")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<AboutResponse> response = TestUtil.parseResponse(
                result,
                ApiResponse.class
        );

        assertTrue(response.isSuccess());
        // Diğer alan kontrolleri eklenebilir
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateAbout_WithAdminRole_ShouldUpdateAndReturnAboutInfo() throws Exception {
        when(aboutService.updateActiveAbout(any(About.class))).thenReturn(testAbout);

        MvcResult result = mockMvc.perform(put("/api/about")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(testAbout)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse<AboutResponse> response = TestUtil.parseResponse(
                result,
                ApiResponse.class
        );

        assertTrue(response.isSuccess());
        assertEquals("About information updated successfully", response.getMessage());
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateAbout_WithUserRole_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(put("/api/about")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(testAbout)))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateAbout_WithoutAuth_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(put("/api/about")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonString(testAbout)))
                .andExpect(status().isUnauthorized());
    }
} 