package com.personal.portfolio.repository;

import com.personal.portfolio.entity.About;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AboutRepository extends JpaRepository<About, Long> {
    Optional<About> findByActiveTrue();
    
    @Modifying
    @Query("UPDATE About a SET a.active = false WHERE a.active = true")
    void deactivateAllAbouts();
} 