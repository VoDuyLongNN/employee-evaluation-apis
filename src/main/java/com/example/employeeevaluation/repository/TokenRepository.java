package com.example.employeeevaluation.repository;

import com.example.employeeevaluation.entity.Token;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByExpireBefore(Date date);

    Optional<Token> findByTokenString(String tokenString);
}
