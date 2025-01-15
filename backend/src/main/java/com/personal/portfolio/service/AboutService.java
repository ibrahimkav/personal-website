package com.personal.portfolio.service;

import com.personal.portfolio.dto.about.AboutRequest;
import com.personal.portfolio.dto.about.AboutResponse;

public interface AboutService {
    AboutResponse getActiveAbout();
    AboutResponse updateActiveAbout(AboutRequest aboutRequest);
    AboutResponse createAbout(AboutRequest aboutRequest);
    void deleteAbout(Long id);
} 