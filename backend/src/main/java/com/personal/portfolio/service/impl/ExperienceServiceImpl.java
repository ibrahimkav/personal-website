package com.personal.portfolio.service.impl;

import com.personal.portfolio.dto.experience.ExperienceRequest;
import com.personal.portfolio.dto.experience.ExperienceResponse;
import com.personal.portfolio.entity.Experience;
import com.personal.portfolio.repository.ExperienceRepository;
import com.personal.portfolio.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ExperienceResponse> getAllExperiences() {
        return experienceRepository.findAllByActiveTrueOrderByOrderAsc()
                .stream()
                .map(this::mapToExperienceResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExperienceResponse> getCurrentExperiences() {
        return experienceRepository.findAllByActiveTrueAndIsCurrentTrueOrderByOrderAsc()
                .stream()
                .map(this::mapToExperienceResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ExperienceResponse getExperienceById(Long id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found with id: " + id));
        return mapToExperienceResponse(experience);
    }

    @Override
    @Transactional
    public ExperienceResponse createExperience(ExperienceRequest experienceRequest) {
        Experience experience = new Experience();
        mapToExperience(experienceRequest, experience);
        return mapToExperienceResponse(experienceRepository.save(experience));
    }

    @Override
    @Transactional
    public ExperienceResponse updateExperience(Long id, ExperienceRequest experienceRequest) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found with id: " + id));
        mapToExperience(experienceRequest, experience);
        return mapToExperienceResponse(experienceRepository.save(experience));
    }

    @Override
    @Transactional
    public void deleteExperience(Long id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found with id: " + id));
        experience.setActive(false);
        experienceRepository.save(experience);
    }

    private ExperienceResponse mapToExperienceResponse(Experience experience) {
        return ExperienceResponse.builder()
                .id(experience.getId())
                .company(experience.getCompany())
                .position(experience.getPosition())
                .description(experience.getDescription())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .isCurrent(experience.getIsCurrent())
                .location(experience.getLocation())
                .companyUrl(experience.getCompanyUrl())
                .companyLogoUrl(experience.getCompanyLogoUrl())
                .order(experience.getOrder())
                .active(experience.getActive())
                .build();
    }

    private void mapToExperience(ExperienceRequest experienceRequest, Experience experience) {
        experience.setCompany(experienceRequest.getCompany());
        experience.setPosition(experienceRequest.getPosition());
        experience.setDescription(experienceRequest.getDescription());
        experience.setStartDate(experienceRequest.getStartDate());
        experience.setEndDate(experienceRequest.getEndDate());
        experience.setIsCurrent(experienceRequest.getIsCurrent());
        experience.setLocation(experienceRequest.getLocation());
        experience.setCompanyUrl(experienceRequest.getCompanyUrl());
        experience.setCompanyLogoUrl(experienceRequest.getCompanyLogoUrl());
        experience.setOrder(experienceRequest.getOrder());
    }
} 