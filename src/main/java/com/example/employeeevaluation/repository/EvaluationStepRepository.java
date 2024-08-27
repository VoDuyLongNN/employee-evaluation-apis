package com.example.employeeevaluation.repository;

import com.example.employeeevaluation.entity.EvaluationStep;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationStepRepository extends JpaRepository<EvaluationStep, Long> {
    List<EvaluationStep> findByEventEventId(Long eventID);
}
