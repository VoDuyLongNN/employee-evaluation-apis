package com.example.employeeevaluation.controller;

import com.example.employeeevaluation.dto.request.InputFieldRequest;
import com.example.employeeevaluation.dto.request.InputFieldUpdateRequest;
import com.example.employeeevaluation.service.IInputFieldService;
import com.example.employeeevaluation.util.AppConstants;
import com.example.employeeevaluation.util.ResultObject;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/admin/input-event")
@PreAuthorize("hasRole('ADMIN')")
public class InputEventController {

    @Autowired
    private IInputFieldService inputFieldService;

    /**
     * Controller: create input field
     * @param request
     * @param result
     * @return <b>ResponseEntity</b>
     */
    @GetMapping("/create")
    public ResponseEntity<ResultObject> create(@Valid @RequestBody List<InputFieldRequest> request, BindingResult result) {
        AppConstants.INFOR_LOGGER.info("controller: Start create input event");

        List<String> errorMessages = new ArrayList<>();
        List<InputFieldRequest> validRequests = new ArrayList<>();

        for (InputFieldRequest inputFieldRequest : request) {
            BindingResult bindingResult = new BeanPropertyBindingResult(inputFieldRequest , "inputFieldRequest");

            if(bindingResult.hasErrors()) {
                List<String> error = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
                errorMessages.addAll(error);
            }

            if(!inputFieldRequest.getUnknownFields().isEmpty()){
                List<String> unknownFieldsMessage = inputFieldRequest.getUnknownFields().keySet().stream()
                    .map(field -> "Failed: Unknown field: " + field)
                    .collect(Collectors.toList());
                errorMessages.addAll(unknownFieldsMessage);
            }

            if (!bindingResult.hasErrors() && inputFieldRequest.getUnknownFields().isEmpty()) {
                validRequests.add(inputFieldRequest);
            }
        }

        return inputFieldService.addInputField(validRequests, errorMessages);
    }

    /**
     * Controller get input field by <b>'Step ID'</b>
     * @param id
     * @return <b>ResponseEntity</b>
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<ResultObject> getByStepId(@PathVariable String id) {
        AppConstants.INFOR_LOGGER.info("controller: get all input event by step id");

        return inputFieldService.getByStepId(id);
    }

    /**
     * Controller get input field by <b>'Input Type'</b>
     * @param type
     * @return <b>ResponseEntity</b>
     */
    @GetMapping("/get-type/{type}")
    public ResponseEntity<ResultObject> getByInputType(@PathVariable String type) {
        AppConstants.INFOR_LOGGER.info("controller: get all input event by input type");

        return inputFieldService.getByInputType(type);
    }

    /**
     * Controller get input fields by <b>'Input Permission'</b>
     * @param permission
     * @return <b>ResponseEntity</b>
     */
    @GetMapping("/get-permission/{permission}")
    public ResponseEntity<ResultObject> getByInputPermission(@PathVariable String permission) {
        AppConstants.INFOR_LOGGER.info("controller: get all input event by input permission");

        return inputFieldService.getByInputPermission(permission);
    }

    /**
     * Controller delete input field by <b>'Input Field ID'</b>
     * @param id
     * @return <b>ResponseEntity</b>
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResultObject> deleteByInputFieldId(@PathVariable String id) {
        AppConstants.INFOR_LOGGER.info("controller: delete input event by input field id");

        return inputFieldService.deleteByInputFieldId(id);
    }

    /**
     * Controller delete input fields by <b>'Step ID'</b>.
     * @param stepId
     * @return <b>ResponseEntity</b>
     */
    @DeleteMapping("/delete-step-id/{stepId}")
    public ResponseEntity<ResultObject> deleteByStepId(@PathVariable String stepId) {
        AppConstants.INFOR_LOGGER.info("controller: delete input event by step id");

        return inputFieldService.deleteByStepId(stepId);
    }

    @PutMapping("/update")
    public ResponseEntity<ResultObject> update(@Valid @RequestBody List<InputFieldUpdateRequest> request, BindingResult result) {
        AppConstants.INFOR_LOGGER.info("controller: Start update input event");

        List<String> errorMessages = new ArrayList<>();
        List<InputFieldUpdateRequest> validRequests = new ArrayList<>();

        for (InputFieldUpdateRequest inputFieldUpdateRequest : request) {
            BindingResult bindingResult = new BeanPropertyBindingResult(inputFieldUpdateRequest , "inputFieldUpdateRequest");

            if(bindingResult.hasErrors()) {
                List<String> error = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
                errorMessages.addAll(error);
            }

            if(!inputFieldUpdateRequest.getUnknownFields().isEmpty()){
                List<String> unknownFieldsMessage = inputFieldUpdateRequest.getUnknownFields().keySet().stream()
                    .map(field -> "Failed: Unknown field: " + field)
                    .collect(Collectors.toList());
                errorMessages.addAll(unknownFieldsMessage);
            }

            if (!bindingResult.hasErrors() && inputFieldUpdateRequest.getUnknownFields().isEmpty()) {
                validRequests.add(inputFieldUpdateRequest);
            }
        }
        return inputFieldService.update(validRequests, errorMessages);
    }
}
