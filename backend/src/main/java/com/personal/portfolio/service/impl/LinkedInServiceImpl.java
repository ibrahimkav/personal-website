package com.personal.portfolio.service.impl;

import com.personal.portfolio.config.LinkedInConfig;
import com.personal.portfolio.dto.linkedin.LinkedInProfileInfo;
import com.personal.portfolio.entity.Testimonial;
import com.personal.portfolio.entity.User;
import com.personal.portfolio.repository.TestimonialRepository;
import com.personal.portfolio.repository.UserRepository;
import com.personal.portfolio.service.LinkedInService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkedInServiceImpl implements LinkedInService {

    private final LinkedInConfig linkedInConfig;
    private final WebClient.Builder webClientBuilder;
    private final UserRepository userRepository;
    private final TestimonialRepository testimonialRepository;

    @Override
    public LinkedInProfileInfo getProfileInfo(String linkedinProfileUrl) {
        // TODO: Implement real LinkedIn API integration
        // For now, extract username from URL and return mock data
        String username = extractUsernameFromUrl(linkedinProfileUrl);
        
        try {
            WebClient client = webClientBuilder
                    .baseUrl("https://api.linkedin.com/v2")
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + linkedInConfig.getClientSecret())
                    .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            // This is a mock call - replace with actual LinkedIn API endpoints
            return client.get()
                    .uri("/me")
                    .retrieve()
                    .bodyToMono(LinkedInProfileInfo.class)
                    .onErrorResume(e -> {
                        log.error("Error fetching LinkedIn profile: {}", e.getMessage());
                        return Mono.just(createMockProfileInfo(username));
                    })
                    .block();
        } catch (Exception e) {
            log.error("Failed to fetch LinkedIn profile, using mock data: {}", e.getMessage());
            return createMockProfileInfo(username);
        }
    }

    @Override
    @Transactional
    public void updateUserProfileFromLinkedIn(Long userId, String linkedinProfileUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LinkedInProfileInfo profileInfo = getProfileInfo(linkedinProfileUrl);

        Testimonial testimonial = testimonialRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Testimonial not found"));

        testimonial.setCompany(profileInfo.getCurrentCompany());
        testimonial.setPosition(profileInfo.getCurrentPosition());
        testimonial.setProfileImageUrl(profileInfo.getProfilePictureUrl());
        testimonial.setLinkedinProfileUrl(profileInfo.getLinkedinProfileUrl());

        testimonialRepository.save(testimonial);
    }

    private String extractUsernameFromUrl(String linkedinProfileUrl) {
        // Example URL: https://www.linkedin.com/in/username
        try {
            String[] parts = linkedinProfileUrl.split("/");
            return parts[parts.length - 1];
        } catch (Exception e) {
            log.error("Error extracting username from LinkedIn URL: {}", e.getMessage());
            return "unknown";
        }
    }

    private LinkedInProfileInfo createMockProfileInfo(String username) {
        return LinkedInProfileInfo.builder()
                .fullName(username)
                .profilePictureUrl("https://example.com/profile.jpg")
                .currentCompany("Example Company")
                .currentPosition("Software Engineer")
                .linkedinProfileUrl("https://linkedin.com/in/" + username)
                .build();
    }
} 