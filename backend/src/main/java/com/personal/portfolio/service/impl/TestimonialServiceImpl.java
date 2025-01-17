package com.personal.portfolio.service.impl;

import com.personal.portfolio.dto.testimonial.TestimonialRequest;
import com.personal.portfolio.dto.testimonial.TestimonialResponse;
import com.personal.portfolio.entity.Testimonial;
import com.personal.portfolio.entity.User;
import com.personal.portfolio.repository.TestimonialRepository;
import com.personal.portfolio.repository.UserRepository;
import com.personal.portfolio.service.LinkedInService;
import com.personal.portfolio.service.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestimonialServiceImpl implements TestimonialService {

    private final TestimonialRepository testimonialRepository;
    private final UserRepository userRepository;
    private final LinkedInService linkedInService;

    @Override
    @Transactional
    public TestimonialResponse createTestimonial(TestimonialRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Testimonial testimonial = new Testimonial();
        testimonial.setUser(user);
        testimonial.setContent(request.getContent());
        testimonial.setLinkedinProfileUrl(request.getLinkedinProfileUrl());
        testimonial.setCreatedAt(LocalDateTime.now());
        testimonial.setUpdatedAt(LocalDateTime.now());
        testimonial.setActive(true);
        testimonial.setIsApproved(false);

        // Save testimonial first
        testimonial = testimonialRepository.save(testimonial);

        try {
            // Try to update LinkedIn info, but don't fail if it doesn't work
            linkedInService.updateUserProfileFromLinkedIn(userId, request.getLinkedinProfileUrl());
        } catch (Exception e) {
            // Log the error but continue
            // The testimonial is already saved, so we can ignore LinkedIn errors
        }

        return mapToTestimonialResponse(testimonial);
    }

    @Override
    @Transactional
    public TestimonialResponse updateTestimonial(Long id, TestimonialRequest request, Long userId) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Testimonial not found"));

        if (!testimonial.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You can only update your own testimonials");
        }

        testimonial.setContent(request.getContent());
        testimonial.setLinkedinProfileUrl(request.getLinkedinProfileUrl());
        testimonial.setUpdatedAt(LocalDateTime.now());
        testimonial.setIsApproved(false); // Reset approval status on update

        // Update LinkedIn info
        linkedInService.updateUserProfileFromLinkedIn(userId, request.getLinkedinProfileUrl());

        return mapToTestimonialResponse(testimonialRepository.save(testimonial));
    }

    @Override
    @Transactional
    public void deleteTestimonial(Long id, Long userId) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Testimonial not found"));

        if (!testimonial.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You can only delete your own testimonials");
        }

        testimonialRepository.delete(testimonial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestimonialResponse> getAllApprovedTestimonials() {
        return testimonialRepository.findByIsApprovedTrueOrderByDisplayOrderAsc()
                .stream()
                .map(this::mapToTestimonialResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestimonialResponse> getAllTestimonials() {
        return testimonialRepository.findAll()
                .stream()
                .map(this::mapToTestimonialResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TestimonialResponse approveTestimonial(Long id) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Testimonial not found"));

        testimonial.setIsApproved(true);
        testimonial.setUpdatedAt(LocalDateTime.now());

        return mapToTestimonialResponse(testimonialRepository.save(testimonial));
    }

    @Override
    @Transactional
    public TestimonialResponse rejectTestimonial(Long id) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Testimonial not found"));

        testimonial.setIsApproved(false);
        testimonial.setUpdatedAt(LocalDateTime.now());

        return mapToTestimonialResponse(testimonialRepository.save(testimonial));
    }

    @Override
    @Transactional
    public TestimonialResponse updateDisplayOrder(Long id, Integer order) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Testimonial not found"));

        testimonial.setDisplayOrder(order);
        testimonial.setUpdatedAt(LocalDateTime.now());

        return mapToTestimonialResponse(testimonialRepository.save(testimonial));
    }

    private TestimonialResponse mapToTestimonialResponse(Testimonial testimonial) {
        return TestimonialResponse.builder()
                .id(testimonial.getId())
                .content(testimonial.getContent())
                .linkedinProfileUrl(testimonial.getLinkedinProfileUrl())
                .profileImageUrl(testimonial.getProfileImageUrl())
                .company(testimonial.getCompany())
                .position(testimonial.getPosition())
                .userFullName(testimonial.getUser().getFullName())
                .isApproved(testimonial.getIsApproved())
                .displayOrder(testimonial.getDisplayOrder())
                .build();
    }
} 