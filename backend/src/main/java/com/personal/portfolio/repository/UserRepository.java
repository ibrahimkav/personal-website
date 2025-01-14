package com.personal.portfolio.repository;

import com.personal.portfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByResetPasswordToken(String token);
    
    Optional<User> findByVerificationToken(String token);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    Optional<User> findByUsernameAndActiveTrue(String username);
    
    List<User> findAllByActiveTrue();
    
    Optional<User> findByIdAndActiveTrue(Long id);
} 