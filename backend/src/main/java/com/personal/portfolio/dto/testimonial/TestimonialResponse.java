package com.personal.portfolio.dto.testimonial;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestimonialResponse {
    private Long id;
    private String content;
    private String linkedinProfileUrl;
    private String profileImageUrl;
    private String company;
    private String position;
    private String userFullName;
    private Boolean isApproved;
    private Integer displayOrder;
} 