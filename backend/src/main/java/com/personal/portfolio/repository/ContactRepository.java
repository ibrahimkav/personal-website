package com.personal.portfolio.repository;

import com.personal.portfolio.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Page<Contact> findByActiveTrueOrderByCreatedAtDesc(Pageable pageable);
    
    Page<Contact> findByReadFalseAndActiveTrueOrderByCreatedAtDesc(Pageable pageable);
    
    long countByReadFalseAndActiveTrue();
    
    Optional<Contact> findByIdAndActiveTrue(Long id);
} 