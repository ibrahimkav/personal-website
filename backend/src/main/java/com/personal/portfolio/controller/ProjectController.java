package com.personal.portfolio.controller;

import com.personal.portfolio.dto.ApiResponse;
import com.personal.portfolio.dto.project.ProjectResponse;
import com.personal.portfolio.entity.Project;
import com.personal.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllProjects() {
        List<Project> projects = projectService.getAllActiveProjects();
        return ResponseEntity.ok(ApiResponse.success(mapToResponseList(projects)));
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getFeaturedProjects() {
        List<Project> projects = projectService.getFeaturedProjects();
        return ResponseEntity.ok(ApiResponse.success(mapToResponseList(projects)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> searchProjects(@RequestParam String name) {
        List<Project> projects = projectService.searchProjects(name);
        return ResponseEntity.ok(ApiResponse.success(mapToResponseList(projects)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProjectResponse>> addProject(@RequestBody Project project) {
        Project savedProject = projectService.save(project);
        return ResponseEntity.ok(ApiResponse.success("Project added successfully", mapToResponse(savedProject)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable Long id,
            @RequestBody Project project) {
        Project updatedProject = projectService.update(id, project);
        return ResponseEntity.ok(ApiResponse.success("Project updated successfully", mapToResponse(updatedProject)));
    }

    @PutMapping("/{id}/order")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProjectOrder(
            @PathVariable Long id,
            @RequestParam Integer order) {
        Project updatedProject = projectService.updateProjectOrder(id, order);
        return ResponseEntity.ok(ApiResponse.success("Project order updated successfully", mapToResponse(updatedProject)));
    }

    @PutMapping("/{id}/featured")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProjectResponse>> toggleProjectFeatured(@PathVariable Long id) {
        Project updatedProject = projectService.toggleProjectFeatured(id);
        return ResponseEntity.ok(ApiResponse.success("Project featured status updated successfully", mapToResponse(updatedProject)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Project deleted successfully", null));
    }

    private List<ProjectResponse> mapToResponseList(List<Project> projects) {
        return projects.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProjectResponse mapToResponse(Project project) {
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
                .build();
    }
} 