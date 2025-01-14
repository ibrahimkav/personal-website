package com.personal.portfolio.repository;

import com.personal.portfolio.entity.About;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AboutRepository extends JpaRepository<About, Long> {
    Optional<About> findFirstByActiveTrue();
} 