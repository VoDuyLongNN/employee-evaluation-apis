package com.example.employeeevaluation.controller;


import com.example.employeeevaluation.dto.EvaluationStepCreateRequest;
import com.example.employeeevaluation.dto.request.EvaluationStepUpdateRequest;
import com.example.employeeevaluation.exceptionhandler.customerException.ApiValidateException;
import com.example.employeeevaluation.service.IEvaluationStepService;
import com.example.employeeevaluation.util.AppConstants;
import com.example.employeeevaluation.util.ResultObject;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.prefix}/evaluation-step")
public class EvaluationStepController {
    @Autowired
    private IEvaluationStepService evaluationStepService;

    @PostMapping("/{eventId}/add-new")
    public ResponseEntity<ResultObject> createEvaluationStep(@RequestBody @Valid EvaluationStepCreateRequest evaluationStepCreateRequest,
                                                             @PathVariable Long eventId, BindingResult bindingResult) {
        AppConstants.INFOR_LOGGER.info("Controller: create evaluation step");
        if (bindingResult.hasErrors()) {
            throw new ApiValidateException("Data Binding Has Error");
        }
        ResultObject resultObject = evaluationStepService.createEvaluationStep(evaluationStepCreateRequest, eventId);
        return new ResponseEntity<>(resultObject, HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ResultObject> updateEvaluationStep(@PathVariable Long id,
                                                             @RequestBody @Valid EvaluationStepUpdateRequest evaluationStepUpdateRequest,
                                                             BindingResult bindingResult) {
        AppConstants.INFOR_LOGGER.info("Controller: update evaluation step");
        if (bindingResult.hasErrors()) {
            throw new ApiValidateException("Data Binding Has Error");
        }
        ResultObject resultObject = evaluationStepService.updateEvaluationStep(id, evaluationStepUpdateRequest);
        return new ResponseEntity<>(resultObject, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ResultObject> deleteEvaluationStep(@PathVariable Long id) {
        AppConstants.INFOR_LOGGER.info("Controller: delete evaluation step");
        ResultObject resultObject = evaluationStepService.deleteEvaluationStep(id);
        return new ResponseEntity<>(resultObject, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultObject> getEvaluationStepById(@PathVariable Long id) {
        AppConstants.INFOR_LOGGER.info("Controller: get evaluation step by id");
        ResultObject resultObject = evaluationStepService.getEvaluationStepById(id);
        return new ResponseEntity<>(resultObject, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ResultObject> getAllEvaluationSteps() {
        AppConstants.INFOR_LOGGER.info("Controller: get all evaluation steps");
        ResultObject resultObject = evaluationStepService.getAllEvaluationSteps();
        return new ResponseEntity<>(resultObject, HttpStatus.OK);
    }



}
