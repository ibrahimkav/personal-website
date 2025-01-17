package com.personal.portfolio.service.impl;

import com.personal.portfolio.dto.about.AboutRequest;
import com.personal.portfolio.dto.about.AboutResponse;
import com.personal.portfolio.entity.About;
import com.personal.portfolio.repository.AboutRepository;
import com.personal.portfolio.service.AboutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AboutServiceImpl implements AboutService {

    private final AboutRepository aboutRepository;

    @Override
    @Transactional(readOnly = true)
    public AboutResponse getActiveAbout() {
        About about = aboutRepository.findByActiveTrue()
                .orElseThrow(() -> new RuntimeException("No active about section found"));
        return mapToAboutResponse(about);
    }

    @Override
    @Transactional
    public AboutResponse updateActiveAbout(AboutRequest aboutRequest) {
        About about = aboutRepository.findByActiveTrue()
                .orElseThrow(() -> new RuntimeException("No active about section found"));
        mapToAbout(aboutRequest, about);
        return mapToAboutResponse(aboutRepository.save(about));
    }

    @Override
    @Transactional
    public AboutResponse createAbout(AboutRequest aboutRequest) {
        About about = new About();
        mapToAbout(aboutRequest, about);
        about.setActive(true);
        about.setCreatedAt(LocalDateTime.now());
        about.setUpdatedAt(LocalDateTime.now());
        aboutRepository.deactivateAllAbouts();
        return mapToAboutResponse(aboutRepository.save(about));
    }

    @Override
    @Transactional
    public void deleteAbout(Long id) {
        About about = aboutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("About not found with id: " + id));
        
        if (about.isActive()) {
            throw new RuntimeException("Cannot delete active about section");
        }
        
        aboutRepository.delete(about);
    }

    private AboutResponse mapToAboutResponse(About about) {
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
                .imageUrl(about.getImageUrl())
                .active(about.isActive())
                .build();
    }

    private void mapToAbout(AboutRequest aboutRequest, About about) {
        about.setFullName(aboutRequest.getFullName());
        about.setTitle(aboutRequest.getTitle());
        about.setDescription(aboutRequest.getDescription());
        about.setLocation(aboutRequest.getLocation());
        about.setEmail(aboutRequest.getEmail());
        about.setPhone(aboutRequest.getPhone());
        about.setLinkedinUrl(aboutRequest.getLinkedinUrl());
        about.setGithubUrl(aboutRequest.getGithubUrl());
        about.setSummary(aboutRequest.getSummary());
        about.setImageUrl(aboutRequest.getImageUrl());
    }
} 