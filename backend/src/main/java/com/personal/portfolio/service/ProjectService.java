package com.personal.portfolio.service;

import com.personal.portfolio.dto.project.ProjectRequest;
import com.personal.portfolio.dto.project.ProjectResponse;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> getAllProjects();
    List<ProjectResponse> getFeaturedProjects();
    ProjectResponse getProjectById(Long id);
    ProjectResponse createProject(ProjectRequest projectRequest);
    ProjectResponse updateProject(Long id, ProjectRequest projectRequest);
    void deleteProject(Long id);
} 