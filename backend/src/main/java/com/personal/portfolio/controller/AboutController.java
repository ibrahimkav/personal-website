package com.personal.portfolio.controller;

import com.personal.portfolio.dto.ApiResponse;
import com.personal.portfolio.dto.about.AboutResponse;
import com.personal.portfolio.entity.About;
import com.personal.portfolio.service.AboutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/about")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AboutController {

    private final AboutService aboutService;

    @GetMapping
    public ResponseEntity<ApiResponse<AboutResponse>> getAbout() {
        About about = aboutService.getActiveAbout();
        return ResponseEntity.ok(ApiResponse.success(mapToResponse(about)));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AboutResponse>> updateAbout(@RequestBody About about) {
        About updatedAbout = aboutService.updateActiveAbout(about);
        return ResponseEntity.ok(ApiResponse.success("About information updated successfully", mapToResponse(updatedAbout)));
    }

    private AboutResponse mapToResponse(About about) {
        return AboutResponse.builder()
                .id(about.getId())
                .fullName(about.getFullName())
                .title(about.getTitle())
                .description(about.getDescription())
                .location(about.getLocation())
                .email(about.getEmail())
                .phone(about.getPhone())
                .linkedinUrl(about.getLinkedinUrl())
                .githubUrl(about.getGithubUrl())
                .summary(about.getSummary())
                .profileImageUrl(about.getProfileImageUrl())
                .build();
    }
} 