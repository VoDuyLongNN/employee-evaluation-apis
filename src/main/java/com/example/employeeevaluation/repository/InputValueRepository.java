package com.example.employeeevaluation.repository;

import com.example.employeeevaluation.entity.InputValue;
import com.example.employeeevaluation.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputValueRepository extends JpaRepository<InputValue, Long> {
    List<InputValue> getAllByUser(User user);
}
