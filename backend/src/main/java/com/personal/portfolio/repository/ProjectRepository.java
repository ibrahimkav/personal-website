package com.personal.portfolio.repository;

import com.personal.portfolio.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByActiveTrueOrderByOrderAsc();
    
    List<Project> findByFeaturedTrueAndActiveTrueOrderByOrderAsc();
    
    List<Project> findByActiveTrueAndNameContainingIgnoreCaseOrderByOrderAsc(String name);
    
    Optional<Project> findByIdAndActiveTrue(Long id);
} 