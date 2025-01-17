package com.personal.portfolio.service;

import com.personal.portfolio.dto.testimonial.TestimonialRequest;
import com.personal.portfolio.dto.testimonial.TestimonialResponse;

import java.util.List;

public interface TestimonialService {
    TestimonialResponse createTestimonial(TestimonialRequest request, Long userId);
    TestimonialResponse updateTestimonial(Long id, TestimonialRequest request, Long userId);
    void deleteTestimonial(Long id, Long userId);
    List<TestimonialResponse> getAllApprovedTestimonials();
    List<TestimonialResponse> getAllTestimonials(); // Admin only
    TestimonialResponse approveTestimonial(Long id); // Admin only
    TestimonialResponse rejectTestimonial(Long id); // Admin only
    TestimonialResponse updateDisplayOrder(Long id, Integer order); // Admin only
} 