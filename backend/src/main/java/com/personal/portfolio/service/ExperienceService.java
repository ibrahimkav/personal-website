package com.personal.portfolio.service;

import com.personal.portfolio.dto.experience.ExperienceRequest;
import com.personal.portfolio.dto.experience.ExperienceResponse;

import java.util.List;

public interface ExperienceService {
    List<ExperienceResponse> getAllExperiences();
    List<ExperienceResponse> getCurrentExperiences();
    ExperienceResponse getExperienceById(Long id);
    ExperienceResponse createExperience(ExperienceRequest experienceRequest);
    ExperienceResponse updateExperience(Long id, ExperienceRequest experienceRequest);
    void deleteExperience(Long id);
} 