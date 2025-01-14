package com.personal.portfolio.service;

import com.personal.portfolio.entity.About;
import com.personal.portfolio.service.base.BaseService;

public interface AboutService extends BaseService<About, Long> {
    About getActiveAbout();
    
    About updateActiveAbout(About about);
} 