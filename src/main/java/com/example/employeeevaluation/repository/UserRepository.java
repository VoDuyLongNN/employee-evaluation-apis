package com.example.employeeevaluation.repository;

import com.example.employeeevaluation.entity.User;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUserName(@NotBlank String username);
    Boolean existsByEmail(@NotBlank String email);
    Optional<User> findByUserName(@NotBlank String username);
    
}
