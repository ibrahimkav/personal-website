package com.personal.portfolio.controller;

import com.personal.portfolio.dto.testimonial.TestimonialRequest;
import com.personal.portfolio.dto.testimonial.TestimonialResponse;
import com.personal.portfolio.security.CurrentUser;
import com.personal.portfolio.security.UserPrincipal;
import com.personal.portfolio.service.TestimonialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testimonials")
@RequiredArgsConstructor
public class TestimonialController {

    private final TestimonialService testimonialService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TestimonialResponse> createTestimonial(
            @Valid @RequestBody TestimonialRequest request,
            @CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok(testimonialService.createTestimonial(request, currentUser.getId()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TestimonialResponse> updateTestimonial(
            @PathVariable Long id,
            @Valid @RequestBody TestimonialRequest request,
            @CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok(testimonialService.updateTestimonial(id, request, currentUser.getId()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteTestimonial(
            @PathVariable Long id,
            @CurrentUser UserPrincipal currentUser) {
        testimonialService.deleteTestimonial(id, currentUser.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<TestimonialResponse>> getAllApprovedTestimonials() {
        return ResponseEntity.ok(testimonialService.getAllApprovedTestimonials());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TestimonialResponse>> getAllTestimonials() {
        return ResponseEntity.ok(testimonialService.getAllTestimonials());
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TestimonialResponse> approveTestimonial(@PathVariable Long id) {
        return ResponseEntity.ok(testimonialService.approveTestimonial(id));
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TestimonialResponse> rejectTestimonial(@PathVariable Long id) {
        return ResponseEntity.ok(testimonialService.rejectTestimonial(id));
    }

    @PutMapping("/{id}/order")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TestimonialResponse> updateDisplayOrder(
            @PathVariable Long id,
            @RequestParam Integer order) {
        return ResponseEntity.ok(testimonialService.updateDisplayOrder(id, order));
    }
} 