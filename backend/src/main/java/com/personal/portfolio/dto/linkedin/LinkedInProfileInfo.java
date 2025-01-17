package com.personal.portfolio.dto.linkedin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkedInProfileInfo {
    private String fullName;
    private String profilePictureUrl;
    private String currentCompany;
    private String currentPosition;
    private String linkedinProfileUrl;
} 