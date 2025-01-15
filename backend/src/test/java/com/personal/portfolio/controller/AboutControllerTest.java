package com.personal.portfolio.controller;

import com.personal.portfolio.dto.about.AboutResponse;
import com.personal.portfolio.dto.ApiResponse;
import com.personal.portfolio.entity.About;
import com.personal.portfolio.service.AboutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AboutControllerTest {

    @Mock
    private AboutService aboutService;

    @InjectMocks
    private AboutController aboutController;

    private About about;
    private AboutResponse aboutResponse;

    @BeforeEach
    void setUp() {
        about = new About();
        about.setId(1L);
        about.setTitle("Test Title");
        about.setDescription("Test Description");

        aboutResponse = new AboutResponse();
        aboutResponse.setId(1L);
        aboutResponse.setTitle("Test Title");
        aboutResponse.setDescription("Test Description");
    }

    @Test
    void getAbout_WhenAboutExists_ShouldReturnAbout() {
        when(aboutService.getActiveAbout()).thenReturn(aboutResponse);

        ResponseEntity<AboutResponse> response = aboutController.getActiveAbout();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AboutResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(aboutResponse.getId(), responseBody.getId());
        assertEquals(aboutResponse.getTitle(), responseBody.getTitle());
        assertEquals(aboutResponse.getDescription(), responseBody.getDescription());
    }

    @Test
    void getAbout_WhenAboutDoesNotExist_ShouldReturnNotFound() {
        when(aboutService.getActiveAbout()).thenReturn(null);

        ResponseEntity<AboutResponse> response = aboutController.getActiveAbout();

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
} 