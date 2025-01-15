package com.personal.portfolio.repository;

import com.personal.portfolio.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Page<Contact> findAllByActiveTrueOrderByCreatedAtDesc(Pageable pageable);
    Page<Contact> findAllByActiveTrueAndIsReadFalseOrderByCreatedAtDesc(Pageable pageable);
    long countByActiveTrueAndIsReadFalse();
} 