package com.example.employeeevaluation.service;

import com.example.employeeevaluation.dto.request.InputFieldRequest;
import com.example.employeeevaluation.dto.request.InputFieldUpdateRequest;
import com.example.employeeevaluation.util.ResultObject;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface IInputFieldService {
    ResponseEntity<ResultObject> addInputField(List<InputFieldRequest> request, List<String> listMessages);

    ResponseEntity<ResultObject> getByStepId(String stepId);

    ResponseEntity<ResultObject> getByInputType(String inputType);

    ResponseEntity<ResultObject> getByInputPermission(String inputPermission);

    ResponseEntity<ResultObject> deleteByInputFieldId(String id);

    ResponseEntity<ResultObject> deleteByStepId(String stepId);

    ResponseEntity<ResultObject> update(List<InputFieldUpdateRequest> listRequests, List<String> listMassages);

}
