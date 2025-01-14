package com.personal.portfolio.service;

import com.personal.portfolio.entity.Experience;
import com.personal.portfolio.entity.Experience.ExperienceType;
import com.personal.portfolio.service.base.BaseService;

import java.util.List;

public interface ExperienceService extends BaseService<Experience, Long> {
    List<Experience> getAllExperiences();
    
    List<Experience> getExperiencesByType(ExperienceType type);
    
    Experience addExperience(Experience experience);
    
    Experience updateExperience(Long id, Experience experience);
} 