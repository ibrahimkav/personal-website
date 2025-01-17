package com.personal.portfolio.dto.testimonial;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TestimonialRequest {
    @NotBlank(message = "Content is required")
    @Size(max = 1000, message = "Content must be less than 1000 characters")
    private String content;

    @NotBlank(message = "LinkedIn profile URL is required")
    @Size(max = 200, message = "LinkedIn profile URL must be less than 200 characters")
    private String linkedinProfileUrl;
} 