package com.personal.portfolio.repository;

import com.personal.portfolio.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByActiveTrueOrderByOrderAsc();
    List<Project> findAllByActiveTrueAndFeaturedTrueOrderByOrderAsc();
} 