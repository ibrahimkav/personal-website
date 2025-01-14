package com.personal.portfolio.service.impl;

import com.personal.portfolio.entity.Experience;
import com.personal.portfolio.entity.Experience.ExperienceType;
import com.personal.portfolio.repository.ExperienceRepository;
import com.personal.portfolio.service.ExperienceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    @Override
    @Transactional
    public Experience save(Experience entity) {
        return experienceRepository.save(entity);
    }

    @Override
    @Transactional
    public Experience update(Long id, Experience entity) {
        if (!experienceRepository.existsById(id)) {
            throw new EntityNotFoundException("Experience not found with id: " + id);
        }
        entity.setId(id);
        return experienceRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Experience experience = findById(id);
        experience.setActive(false);
        experienceRepository.save(experience);
    }

    @Override
    public Experience findById(Long id) {
        return experienceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Experience not found with id: " + id));
    }

    @Override
    public List<Experience> findAll() {
        return experienceRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return experienceRepository.existsById(id);
    }

    @Override
    public List<Experience> getAllExperiences() {
        return experienceRepository.findByActiveTrueOrderByStartDateDesc();
    }

    @Override
    public List<Experience> getExperiencesByType(ExperienceType type) {
        return experienceRepository.findByTypeAndActiveTrueOrderByStartDateDesc(type);
    }

    @Override
    @Transactional
    public Experience addExperience(Experience experience) {
        return save(experience);
    }

    @Override
    @Transactional
    public Experience updateExperience(Long id, Experience experience) {
        return update(id, experience);
    }
} 