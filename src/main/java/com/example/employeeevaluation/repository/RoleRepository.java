package com.example.employeeevaluation.repository;

import com.example.employeeevaluation.entity.Role;
import com.example.employeeevaluation.entity.enums.ERole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ERole name);
}
