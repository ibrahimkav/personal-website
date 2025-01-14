package com.personal.portfolio.service.impl;

import com.personal.portfolio.entity.Project;
import com.personal.portfolio.repository.ProjectRepository;
import com.personal.portfolio.service.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public Project save(Project entity) {
        return projectRepository.save(entity);
    }

    @Override
    @Transactional
    public Project update(Long id, Project entity) {
        if (!projectRepository.existsById(id)) {
            throw new EntityNotFoundException("Project not found with id: " + id);
        }
        entity.setId(id);
        return projectRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Project project = findById(id);
        project.setActive(false);
        projectRepository.save(project);
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return projectRepository.existsById(id);
    }

    @Override
    public List<Project> getAllActiveProjects() {
        return projectRepository.findByActiveTrueOrderByOrderAsc();
    }

    @Override
    public List<Project> getFeaturedProjects() {
        return projectRepository.findByFeaturedTrueAndActiveTrueOrderByOrderAsc();
    }

    @Override
    public List<Project> searchProjects(String name) {
        return projectRepository.findByActiveTrueAndNameContainingIgnoreCaseOrderByOrderAsc(name);
    }

    @Override
    @Transactional
    public Project updateProjectOrder(Long id, Integer order) {
        Project project = findById(id);
        project.setOrder(order);
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public Project toggleProjectFeatured(Long id) {
        Project project = findById(id);
        project.setFeatured(!project.getFeatured());
        return projectRepository.save(project);
    }
} 