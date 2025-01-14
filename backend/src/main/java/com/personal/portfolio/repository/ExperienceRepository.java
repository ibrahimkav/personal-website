package com.personal.portfolio.repository;

import com.personal.portfolio.entity.Experience;
import com.personal.portfolio.entity.Experience.ExperienceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByTypeAndActiveTrueOrderByStartDateDesc(ExperienceType type);
    
    List<Experience> findByActiveTrueOrderByStartDateDesc();
    
    Optional<Experience> findByIdAndActiveTrue(Long id);
} 