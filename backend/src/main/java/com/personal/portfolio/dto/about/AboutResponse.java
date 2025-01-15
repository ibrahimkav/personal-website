package com.personal.portfolio.dto.about;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutResponse {
    private Long id;
    private String fullName;
    private String title;
    private String description;
    private String location;
    private String email;
    private String phone;
    private String linkedinUrl;
    private String githubUrl;
    private String summary;
    private String profileImageUrl;
    private String imageUrl;
    private Boolean active;
} 