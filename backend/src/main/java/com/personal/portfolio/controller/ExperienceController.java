package com.personal.portfolio.controller;

import com.personal.portfolio.dto.experience.ExperienceRequest;
import com.personal.portfolio.dto.experience.ExperienceResponse;
import com.personal.portfolio.service.ExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping
    public ResponseEntity<List<ExperienceResponse>> getAllExperiences() {
        return ResponseEntity.ok(experienceService.getAllExperiences());
    }

    @GetMapping("/current")
    public ResponseEntity<List<ExperienceResponse>> getCurrentExperiences() {
        return ResponseEntity.ok(experienceService.getCurrentExperiences());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExperienceResponse> getExperienceById(@PathVariable Long id) {
        return ResponseEntity.ok(experienceService.getExperienceById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExperienceResponse> createExperience(@Valid @RequestBody ExperienceRequest experienceRequest) {
        return ResponseEntity.ok(experienceService.createExperience(experienceRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExperienceResponse> updateExperience(
            @PathVariable Long id,
            @Valid @RequestBody ExperienceRequest experienceRequest) {
        return ResponseEntity.ok(experienceService.updateExperience(id, experienceRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        experienceService.deleteExperience(id);
        return ResponseEntity.ok().build();
    }
} 