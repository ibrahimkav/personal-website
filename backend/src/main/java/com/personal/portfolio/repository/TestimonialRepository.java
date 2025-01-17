package com.personal.portfolio.repository;

import com.personal.portfolio.entity.Testimonial;
import com.personal.portfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {
    Optional<Testimonial> findByUser(User user);
    List<Testimonial> findByIsApprovedTrueOrderByDisplayOrderAsc();
} 