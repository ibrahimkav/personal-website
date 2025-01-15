package com.personal.portfolio.repository;

import com.personal.portfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndActiveTrue(String username);
    Optional<User> findByEmailAndActiveTrue(String email);
    Optional<User> findByVerificationTokenAndActiveTrue(String token);
    boolean existsByUsernameAndActiveTrue(String username);
    boolean existsByEmailAndActiveTrue(String email);
} 