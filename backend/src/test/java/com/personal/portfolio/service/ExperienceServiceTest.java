package com.personal.portfolio.service;

import com.personal.portfolio.entity.Experience;
import com.personal.portfolio.entity.Experience.ExperienceType;
import com.personal.portfolio.exception.EntityNotFoundException;
import com.personal.portfolio.repository.ExperienceRepository;
import com.personal.portfolio.service.impl.ExperienceServiceImpl;
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
class ExperienceServiceTest {

    @Mock
    private ExperienceRepository experienceRepository;

    @InjectMocks
    private ExperienceServiceImpl experienceService;

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
    void getAllExperiences_ShouldReturnExperiences() {
        when(experienceRepository.findAllByActiveTrue()).thenReturn(testExperiences);

        List<Experience> result = experienceService.getAllExperiences();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(experienceRepository, times(1)).findAllByActiveTrue();
    }

    @Test
    void getExperiencesByType_ShouldReturnFilteredExperiences() {
        when(experienceRepository.findAllByTypeAndActiveTrue(ExperienceType.WORK))
                .thenReturn(List.of(testExperience));

        List<Experience> result = experienceService.getExperiencesByType(ExperienceType.WORK);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ExperienceType.WORK, result.get(0).getType());
        verify(experienceRepository, times(1))
                .findAllByTypeAndActiveTrue(ExperienceType.WORK);
    }

    @Test
    void getExperienceById_ShouldReturnExperience_WhenExists() {
        when(experienceRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testExperience));

        Experience result = experienceService.getExperienceById(1L);

        assertNotNull(result);
        assertEquals(testExperience.getTitle(), result.getTitle());
        assertEquals(testExperience.getOrganization(), result.getOrganization());
        verify(experienceRepository, times(1)).findByIdAndActiveTrue(1L);
    }

    @Test
    void getExperienceById_ShouldThrowException_WhenNotExists() {
        when(experienceRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> experienceService.getExperienceById(1L));
        verify(experienceRepository, times(1)).findByIdAndActiveTrue(1L);
    }

    @Test
    void addExperience_ShouldAddAndReturnExperience() {
        when(experienceRepository.save(any(Experience.class))).thenReturn(testExperience);

        Experience result = experienceService.addExperience(testExperience);

        assertNotNull(result);
        assertEquals(testExperience.getTitle(), result.getTitle());
        assertEquals(testExperience.getOrganization(), result.getOrganization());
        verify(experienceRepository, times(1)).save(any(Experience.class));
    }

    @Test
    void updateExperience_ShouldUpdateAndReturnExperience_WhenExists() {
        Experience updatedExperience = TestUtil.createTestExperience();
        updatedExperience.setTitle("Updated Title");
        updatedExperience.setOrganization("Updated Organization");

        when(experienceRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testExperience));
        when(experienceRepository.save(any(Experience.class))).thenReturn(updatedExperience);

        Experience result = experienceService.updateExperience(1L, updatedExperience);

        assertNotNull(result);
        assertEquals(updatedExperience.getTitle(), result.getTitle());
        assertEquals(updatedExperience.getOrganization(), result.getOrganization());
        verify(experienceRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(experienceRepository, times(1)).save(any(Experience.class));
    }

    @Test
    void updateExperience_ShouldThrowException_WhenNotExists() {
        when(experienceRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> experienceService.updateExperience(1L, testExperience));
        verify(experienceRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(experienceRepository, never()).save(any(Experience.class));
    }

    @Test
    void deleteExperience_ShouldSetActiveToFalse_WhenExists() {
        when(experienceRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.of(testExperience));
        when(experienceRepository.save(any(Experience.class))).thenReturn(testExperience);

        experienceService.deleteExperience(1L);

        assertFalse(testExperience.isActive());
        verify(experienceRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(experienceRepository, times(1)).save(any(Experience.class));
    }

    @Test
    void deleteExperience_ShouldThrowException_WhenNotExists() {
        when(experienceRepository.findByIdAndActiveTrue(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> experienceService.deleteExperience(1L));
        verify(experienceRepository, times(1)).findByIdAndActiveTrue(1L);
        verify(experienceRepository, never()).save(any(Experience.class));
    }
} 