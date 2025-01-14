package com.personal.portfolio.controller;

import com.personal.portfolio.dto.ApiResponse;
import com.personal.portfolio.dto.experience.ExperienceResponse;
import com.personal.portfolio.entity.Experience;
import com.personal.portfolio.entity.Experience.ExperienceType;
import com.personal.portfolio.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExperienceResponse>>> getAllExperiences() {
        List<Experience> experiences = experienceService.getAllExperiences();
        return ResponseEntity.ok(ApiResponse.success(mapToResponseList(experiences)));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<ExperienceResponse>>> getExperiencesByType(@PathVariable ExperienceType type) {
        List<Experience> experiences = experienceService.getExperiencesByType(type);
        return ResponseEntity.ok(ApiResponse.success(mapToResponseList(experiences)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ExperienceResponse>> addExperience(@RequestBody Experience experience) {
        Experience savedExperience = experienceService.addExperience(experience);
        return ResponseEntity.ok(ApiResponse.success("Experience added successfully", mapToResponse(savedExperience)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ExperienceResponse>> updateExperience(
            @PathVariable Long id,
            @RequestBody Experience experience) {
        Experience updatedExperience = experienceService.updateExperience(id, experience);
        return ResponseEntity.ok(ApiResponse.success("Experience updated successfully", mapToResponse(updatedExperience)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteExperience(@PathVariable Long id) {
        experienceService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Experience deleted successfully", null));
    }

    private List<ExperienceResponse> mapToResponseList(List<Experience> experiences) {
        return experiences.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ExperienceResponse mapToResponse(Experience experience) {
        return ExperienceResponse.builder()
                .id(experience.getId())
                .title(experience.getTitle())
                .organization(experience.getOrganization())
                .description(experience.getDescription())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .location(experience.getLocation())
                .type(experience.getType())
                .technologies(experience.getTechnologies())
                .certificateUrl(experience.getCertificateUrl())
                .build();
    }
} 