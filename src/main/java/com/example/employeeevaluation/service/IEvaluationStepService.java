package com.example.employeeevaluation.service;

import com.example.employeeevaluation.dto.EvaluationStepCreateRequest;
import com.example.employeeevaluation.dto.request.EvaluationStepUpdateRequest;
import com.example.employeeevaluation.util.ResultObject;

public interface IEvaluationStepService {
    ResultObject createEvaluationStep(EvaluationStepCreateRequest createRequest, Long evenId);
    ResultObject updateEvaluationStep(Long id, EvaluationStepUpdateRequest updateRequest);
    ResultObject deleteEvaluationStep(Long id);
    ResultObject getEvaluationStepById(Long id);
    ResultObject getAllEvaluationSteps();
}
