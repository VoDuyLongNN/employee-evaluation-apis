package com.example.employeeevaluation.repository;

import com.example.employeeevaluation.entity.EvaluationStep;
import com.example.employeeevaluation.entity.InputField;
import com.example.employeeevaluation.entity.enums.EInputPermission;
import com.example.employeeevaluation.entity.enums.EInputType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputFieldRepository extends JpaRepository<InputField, Long> {
    List<InputField> getAllByEvaluationStep(EvaluationStep step);

    List<InputField> getAllByType(EInputType type);

    List<InputField> getAllByPermission(EInputPermission type);

    List<InputField> findAllByEvaluationStep(EvaluationStep step);
}
