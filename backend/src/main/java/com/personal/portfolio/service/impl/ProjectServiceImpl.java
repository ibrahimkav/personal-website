package com.personal.portfolio.service.impl;

import com.personal.portfolio.dto.project.ProjectRequest;
import com.personal.portfolio.dto.project.ProjectResponse;
import com.personal.portfolio.entity.Project;
import com.personal.portfolio.repository.ProjectRepository;
import com.personal.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAllByActiveTrueOrderByOrderAsc()
                .stream()
                .map(this::mapToProjectResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getFeaturedProjects() {
        return projectRepository.findAllByActiveTrueAndFeaturedTrueOrderByOrderAsc()
                .stream()
                .map(this::mapToProjectResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        return mapToProjectResponse(project);
    }

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        Project project = new Project();
        mapToProject(projectRequest, project);
        return mapToProjectResponse(projectRepository.save(project));
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        mapToProject(projectRequest, project);
        return mapToProjectResponse(projectRepository.save(project));
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        project.setActive(false);
        projectRepository.save(project);
    }

    private ProjectResponse mapToProjectResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .technologies(project.getTechnologies())
                .githubUrl(project.getGithubUrl())
                .demoUrl(project.getDemoUrl())
                .imageUrl(project.getImageUrl())
                .featured(project.getFeatured())
                .order(project.getOrder())
                .active(project.getActive())
                .build();
    }

    private void mapToProject(ProjectRequest projectRequest, Project project) {
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setTechnologies(projectRequest.getTechnologies());
        project.setGithubUrl(projectRequest.getGithubUrl());
        project.setDemoUrl(projectRequest.getDemoUrl());
        project.setImageUrl(projectRequest.getImageUrl());
        project.setFeatured(projectRequest.getFeatured());
        project.setOrder(projectRequest.getOrder());
    }
} 