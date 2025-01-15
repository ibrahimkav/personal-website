package com.personal.portfolio.repository;

import com.personal.portfolio.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findAllByActiveTrueOrderByOrderAsc();
    List<Experience> findAllByActiveTrueAndIsCurrentTrueOrderByOrderAsc();
} 