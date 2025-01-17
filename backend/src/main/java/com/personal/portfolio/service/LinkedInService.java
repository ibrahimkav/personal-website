package com.personal.portfolio.service;

import com.personal.portfolio.dto.linkedin.LinkedInProfileInfo;

public interface LinkedInService {
    LinkedInProfileInfo getProfileInfo(String linkedinProfileUrl);
    void updateUserProfileFromLinkedIn(Long userId, String linkedinProfileUrl);
} 