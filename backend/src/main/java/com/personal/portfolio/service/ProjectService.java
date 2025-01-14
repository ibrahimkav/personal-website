package com.personal.portfolio.service;

import com.personal.portfolio.entity.Project;
import com.personal.portfolio.service.base.BaseService;

import java.util.List;

public interface ProjectService extends BaseService<Project, Long> {
    List<Project> getAllActiveProjects();
    
    List<Project> getFeaturedProjects();
    
    List<Project> searchProjects(String name);
    
    Project updateProjectOrder(Long id, Integer order);
    
    Project toggleProjectFeatured(Long id);
} 