package com.example.employeeevaluation.service.impl;

import com.example.employeeevaluation.dto.EvaluationStepCreateRequest;
import com.example.employeeevaluation.dto.request.EvaluationStepUpdateRequest;
import com.example.employeeevaluation.entity.EvaluationStep;
import com.example.employeeevaluation.entity.Event;
import com.example.employeeevaluation.exceptionhandler.customerException.ApiValidateException;
import com.example.employeeevaluation.repository.EvaluationStepRepository;
import com.example.employeeevaluation.repository.EventRepository;
import com.example.employeeevaluation.service.IEvaluationStepService;
import com.example.employeeevaluation.util.AppConstants;
import com.example.employeeevaluation.util.ResultObject;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EvaluationStepServiceImpl implements IEvaluationStepService {
    private final EvaluationStepRepository evaluationStepRepository;
    private final EventRepository eventRepository;

    public EvaluationStepServiceImpl(EvaluationStepRepository evaluationStepRepository, EventRepository eventRepository) {
        this.evaluationStepRepository = evaluationStepRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public ResultObject createEvaluationStep(EvaluationStepCreateRequest createRequest, Long eventId) {
        try {
            Event event = eventRepository.findById(createRequest.getEventId())
                    .orElseThrow(() -> new ApiValidateException("Event Not Found"));

            EvaluationStep evaluationStep = EvaluationStep.builder()
                    .stepName(createRequest.getStepName())
                    .event(event)
                    .level(createRequest.getLevel())
                    .build();

            EvaluationStep savedStep = evaluationStepRepository.save(evaluationStep);

            return ResultObject.builder().status("OK").messages(Collections.singletonList("Evaluation Step created successfully")).data(savedStep).build();
        } catch (ApiValidateException e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList(e.getMessage())).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
        }
    }

    @Override
    public ResultObject updateEvaluationStep(Long id, EvaluationStepUpdateRequest updateRequest) {
        try {
            EvaluationStep step = evaluationStepRepository.findById(id)
                    .orElseThrow(() -> new ApiValidateException("Evaluation Step Not Found"));

            step.setStepName(updateRequest.getStepName());
            step.setLevel(updateRequest.getLevel());

            Event event = eventRepository.findById(updateRequest.getEventId())
                    .orElseThrow(() -> new ApiValidateException("Event Not Found"));

            step.setEvent(event);

            EvaluationStep updatedStep = evaluationStepRepository.save(step);

            return ResultObject.builder().status("OK").messages(Collections.singletonList("Evaluation Step updated successfully")).data(updatedStep).build();
        } catch (ApiValidateException e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList(e.getMessage())).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
        }
    }

    @Override
    public ResultObject deleteEvaluationStep(Long id) {
        try {
            EvaluationStep step = evaluationStepRepository.findById(id)
                    .orElseThrow(() -> new ApiValidateException("Evaluation Step Not Found"));

            evaluationStepRepository.delete(step);

            return ResultObject.builder().status("OK").messages(Collections.singletonList("Evaluation Step deleted successfully")).build();
        } catch (ApiValidateException e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList(e.getMessage())).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
        }
    }

    @Override
    public ResultObject getEvaluationStepById(Long id) {
        try {
            EvaluationStep step = evaluationStepRepository.findById(id)
                    .orElseThrow(() -> new ApiValidateException("Evaluation Step Not Found"));

            return ResultObject.builder().status("OK").messages(Collections.singletonList("Evaluation Step retrieved successfully")).data(step).build();
        } catch (ApiValidateException e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList(e.getMessage())).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
        }
    }

    @Override
    public ResultObject getAllEvaluationSteps() {
        try {
            List<EvaluationStep> steps = evaluationStepRepository.findAll();

            return ResultObject.builder().status("OK").messages(Collections.singletonList("Evaluation Steps retrieved successfully")).data(steps).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
        }
    }
}
