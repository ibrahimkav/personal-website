package com.personal.portfolio.service;

import com.personal.portfolio.entity.About;
import com.personal.portfolio.exception.EntityNotFoundException;
import com.personal.portfolio.repository.AboutRepository;
import com.personal.portfolio.service.impl.AboutServiceImpl;
import com.personal.portfolio.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AboutServiceTest {

    @Mock
    private AboutRepository aboutRepository;

    @InjectMocks
    private AboutServiceImpl aboutService;

    private About testAbout;

    @BeforeEach
    void setUp() {
        testAbout = TestUtil.createTestAbout();
    }

    @Test
    void getActiveAbout_ShouldReturnAbout_WhenExists() {
        when(aboutRepository.findByActiveTrue()).thenReturn(Optional.of(testAbout));

        About result = aboutService.getActiveAbout();

        assertNotNull(result);
        assertEquals(testAbout.getFullName(), result.getFullName());
        assertEquals(testAbout.getTitle(), result.getTitle());
        verify(aboutRepository, times(1)).findByActiveTrue();
    }

    @Test
    void getActiveAbout_ShouldThrowException_WhenNotExists() {
        when(aboutRepository.findByActiveTrue()).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> aboutService.getActiveAbout());
        verify(aboutRepository, times(1)).findByActiveTrue();
    }

    @Test
    void updateActiveAbout_ShouldUpdateExisting_WhenExists() {
        About updatedAbout = TestUtil.createTestAbout();
        updatedAbout.setFullName("Updated Name");
        updatedAbout.setTitle("Updated Title");

        when(aboutRepository.findByActiveTrue()).thenReturn(Optional.of(testAbout));
        when(aboutRepository.save(any(About.class))).thenReturn(updatedAbout);

        About result = aboutService.updateActiveAbout(updatedAbout);

        assertNotNull(result);
        assertEquals(updatedAbout.getFullName(), result.getFullName());
        assertEquals(updatedAbout.getTitle(), result.getTitle());
        verify(aboutRepository, times(1)).findByActiveTrue();
        verify(aboutRepository, times(1)).save(any(About.class));
    }

    @Test
    void updateActiveAbout_ShouldThrowException_WhenNotExists() {
        when(aboutRepository.findByActiveTrue()).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> aboutService.updateActiveAbout(testAbout));
        verify(aboutRepository, times(1)).findByActiveTrue();
        verify(aboutRepository, never()).save(any(About.class));
    }

    @Test
    void save_ShouldSaveAndReturnAbout() {
        when(aboutRepository.save(any(About.class))).thenReturn(testAbout);

        About result = aboutService.save(testAbout);

        assertNotNull(result);
        assertEquals(testAbout.getFullName(), result.getFullName());
        assertEquals(testAbout.getTitle(), result.getTitle());
        verify(aboutRepository, times(1)).save(any(About.class));
    }

    @Test
    void delete_ShouldSetActiveToFalse() {
        when(aboutRepository.findByActiveTrue()).thenReturn(Optional.of(testAbout));
        when(aboutRepository.save(any(About.class))).thenReturn(testAbout);

        aboutService.delete();

        assertFalse(testAbout.isActive());
        verify(aboutRepository, times(1)).findByActiveTrue();
        verify(aboutRepository, times(1)).save(any(About.class));
    }
} 