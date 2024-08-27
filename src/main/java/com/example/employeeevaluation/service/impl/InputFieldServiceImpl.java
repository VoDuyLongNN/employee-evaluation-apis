package com.example.employeeevaluation.service.impl;

import com.example.employeeevaluation.config.LocalizationUtils;
import com.example.employeeevaluation.dto.request.InputFieldRequest;
import com.example.employeeevaluation.dto.request.InputFieldUpdateRequest;
import com.example.employeeevaluation.dto.response.InputFieldResponse;
import com.example.employeeevaluation.entity.EvaluationStep;
import com.example.employeeevaluation.entity.InputField;
import com.example.employeeevaluation.entity.User;
import com.example.employeeevaluation.entity.enums.EInputPermission;
import com.example.employeeevaluation.entity.enums.EInputType;
import com.example.employeeevaluation.exceptionhandler.customerException.ApiValidateException;
import com.example.employeeevaluation.repository.EvaluationStepRepository;
import com.example.employeeevaluation.repository.InputFieldRepository;
import com.example.employeeevaluation.service.IInputFieldService;
import com.example.employeeevaluation.util.AppConstants;
import com.example.employeeevaluation.util.InputValidator;
import com.example.employeeevaluation.util.MessageKeys;
import com.example.employeeevaluation.util.ResultObject;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class InputFieldServiceImpl implements IInputFieldService {

    @Autowired
    private LocalizationUtils localizationUtils;

    @Autowired
    private InputFieldRepository inputFieldRepository;

    @Autowired
    private EvaluationStepRepository evaluationStepRepository;

    /**
     * Check validate and add one or many <b>Input Fields</b>
     * @param requests
     * @param listMassages
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<ResultObject> addInputField(List<InputFieldRequest> requests, List<String> listMassages) {
        List<InputFieldResponse> inputFieldResponses = new ArrayList<>();
        List<String> messages = listMassages;
        ResultObject resultObject;
        InputField inputField;
        HttpStatus httpStatus;
        User user;
        boolean insertSuccess = false;

        try{
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(requests.isEmpty()){
                throw new ApiValidateException("Failed: All item is not correct!");
            }
            for(InputFieldRequest inputFieldRequest : requests){
                if (!InputValidator.validateInputType(inputFieldRequest.getType())) {
                    messages.add("Failed: Invalid input type: " + inputFieldRequest.getType());
                    continue;
                }

                if(!InputValidator.validateInputPermission(inputFieldRequest.getPermission())) {
                    messages.add("Failed: Invalid input permission: " + inputFieldRequest.getPermission());
                    continue;
                }

                if(inputFieldRequest.getStepId() == null || !InputValidator.isLong(inputFieldRequest.getStepId())){
                    messages.add("Failed: Invalid step ID: " + inputFieldRequest.getStepId());
                    continue;
                }

                Optional<EvaluationStep> optionalStep = evaluationStepRepository.findById(Long.parseLong(inputFieldRequest.getStepId()));

                if(!optionalStep.isPresent()) {
                    messages.add("Failed: Step id " + inputFieldRequest.getStepId() + " is not found");
                } else {
                    EvaluationStep step = optionalStep.get();
                    inputField = InputField.builder()
                        .label(inputFieldRequest.getLabel())
                        .type(EInputType.valueOf(inputFieldRequest.getType()))
                        .permission(EInputPermission.valueOf(inputFieldRequest.getPermission()))
                        .evaluationStep(step)
                        .user(user)
                        .build();

                    inputFieldRepository.save(inputField);

                    messages.add(localizationUtils.getLocalizedMessage(MessageKeys.ADD_INPUT_FIELD));
                    InputFieldResponse response = new InputFieldResponse().convertToDTO(inputField);
                    inputFieldResponses.add(response);
                    insertSuccess = true;
                }
            }
            if(insertSuccess){
                messages.addFirst("Number of records added: " + inputFieldResponses.size());
                resultObject = ResultObject.builder()
                    .status(HttpStatus.OK.name())
                    .messages(messages)
                    .data(inputFieldResponses)
                    .build();

                httpStatus = HttpStatus.OK;
            } else {
                throw new ApiValidateException("All item is not correct!");
            }

        } catch (ApiValidateException e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            resultObject = ResultObject.builder()
                    .status(HttpStatus.BAD_REQUEST.name())
                    .messages(Collections.singletonList(e.getMessage()))
                    .build();

            httpStatus = HttpStatus.BAD_REQUEST;

        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            resultObject = ResultObject.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .messages(Collections.singletonList(e.getMessage()))
                    .build();

            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultObject, httpStatus);
    }

    /**
     * Get input fields by <b>'Step ID'</b>
     * @param stepId
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<ResultObject> getByStepId(String stepId) {
        ResultObject resultObject;
        List<InputField> inputFields;
        List<InputFieldResponse> inputFieldResponses;
        HttpStatus httpStatus;

        try{
            if(!InputValidator.isLong(stepId)){
                throw new ApiValidateException("Failed: Invalid step ID: " + stepId);
            }

            EvaluationStep step = evaluationStepRepository.findById(Long.parseLong(stepId)).orElseThrow(() -> new ApiValidateException("Step id is not found"));
            inputFields = inputFieldRepository.getAllByEvaluationStep(step);
            inputFieldResponses = new InputFieldResponse().convertToListDTO(inputFields);

            resultObject = ResultObject.builder()
                    .status(HttpStatus.OK.name())
                    .messages(Collections.singletonList(localizationUtils.getLocalizedMessage(MessageKeys.GET_INPUT_FIELD)))
                    .data(inputFieldResponses)
                    .build();

            httpStatus = HttpStatus.OK;

        } catch (ApiValidateException e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            resultObject = ResultObject.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .messages(Collections.singletonList(e.getMessage()))
                .build();

            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            resultObject = ResultObject.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .messages(Collections.singletonList(e.getMessage()))
                    .build();

            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultObject, httpStatus);
    }

    /**
     * Get input fields by <b>'Input Type'</b>
     * @param inputType
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<ResultObject> getByInputType(String inputType) {
        ResultObject resultObject;
        List<InputField> inputFields;
        List<InputFieldResponse> inputFieldResponses;
        HttpStatus httpStatus;
        String type = inputType.toUpperCase();

        try{
            if(!InputValidator.validateInputType(type)){
                throw new ApiValidateException("Failed: Invalid input type: " + inputType);
            }

            if(type.isEmpty()){
                throw new ApiValidateException("Failed: Empty input type");
            }

            inputFields = inputFieldRepository.getAllByType(EInputType.valueOf(type));
            inputFieldResponses = new InputFieldResponse().convertToListDTO(inputFields);

            resultObject = ResultObject.builder()
                .status(HttpStatus.OK.name())
                .messages(Collections.singletonList("SUCCESSFULLY"))
                .data(inputFieldResponses)
                .build();

            httpStatus = HttpStatus.OK;
        } catch (ApiValidateException e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());

            resultObject = ResultObject.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .messages(Collections.singletonList(e.getMessage()))
                .build();

            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());

            resultObject = ResultObject.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .messages(Collections.singletonList(e.getMessage()))
                .build();

            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultObject, httpStatus);
    }

    /**
     * Get input fields <b>'Input Permission'</b>
     * @param inputPermission
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<ResultObject> getByInputPermission(String inputPermission) {
        ResultObject resultObject;
        List<InputField> inputFields;
        List<InputFieldResponse> inputFieldResponses;
        HttpStatus httpStatus;
        String permission = inputPermission.toUpperCase();

        try{
            if(!InputValidator.validateInputPermission(permission)){
                throw new ApiValidateException("Failed: Invalid input permission: " + inputPermission);
            }

            if(permission.isEmpty()){
                throw new ApiValidateException("Failed: Empty input permission");
            }

            inputFields = inputFieldRepository.getAllByPermission(EInputPermission.valueOf(permission));
            inputFieldResponses = new InputFieldResponse().convertToListDTO(inputFields);

            resultObject = ResultObject.builder()
                .status(HttpStatus.OK.name())
                .messages(Collections.singletonList("SUCCESSFULLY"))
                .data(inputFieldResponses)
                .build();

            httpStatus = HttpStatus.OK;
        } catch (ApiValidateException e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());

            resultObject = ResultObject.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .messages(Collections.singletonList(e.getMessage()))
                .build();

            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());

            resultObject = ResultObject.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .messages(Collections.singletonList(e.getMessage()))
                .build();

            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultObject, httpStatus);
    }

    /**
     * Delete input field by <b>'Input Field ID</b>
     * @param id
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<ResultObject> deleteByInputFieldId(String id) {
        ResultObject resultObject;
        HttpStatus httpStatus;
        try{
            if(!InputValidator.isLong(id)){
                throw new ApiValidateException("Failed: Invalid step ID: " + id);
            }

            InputField inputField = inputFieldRepository.findById(Long.parseLong(id)).orElseThrow(() -> new ApiValidateException("Input field not found"));

            inputFieldRepository.delete(inputField);

            resultObject = ResultObject.builder()
                    .status(HttpStatus.OK.name())
                    .messages(Collections.singletonList(localizationUtils.getLocalizedMessage(MessageKeys.DELETE_INPUT_FIELD)))
                    .build();

            httpStatus = HttpStatus.OK;

        } catch (ApiValidateException e){
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            resultObject = ResultObject.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .messages(Collections.singletonList(e.getMessage()))
                .build();

            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            resultObject = ResultObject.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .messages(Collections.singletonList(e.getMessage()))
                    .build();

            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultObject, httpStatus);
    }

    /**
     * Delete input fields by <b>'Step ID'</b>
     * @param stepId
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<ResultObject> deleteByStepId(String stepId) {
        ResultObject resultObject;
        HttpStatus httpStatus;
        int numOfDeleteRecords = 0;
        List<InputField> inputFields;

        try{
            if(!InputValidator.isLong(stepId)){
                throw new ApiValidateException("Failed: Invalid step ID: " + stepId);
            }

            EvaluationStep step = evaluationStepRepository.findById(Long.parseLong(stepId)).orElseThrow(() -> new ApiValidateException("Step id is not found"));
            inputFields = inputFieldRepository.findAllByEvaluationStep(step);

            if(inputFields.isEmpty()){
                throw new ApiValidateException("Step with id " + stepId + " is not have any input field");
            }

            numOfDeleteRecords = inputFields.size();

            inputFieldRepository.deleteAll(inputFields);

            resultObject = ResultObject.builder()
                .status(HttpStatus.OK.name())
                .messages(Collections.singletonList("Successfully, number of records deleted: " + numOfDeleteRecords))
                .build();

            httpStatus = HttpStatus.OK;

        } catch (ApiValidateException e){
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            resultObject = ResultObject.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .messages(Collections.singletonList(e.getMessage()))
                .build();

            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            resultObject = ResultObject.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .messages(Collections.singletonList(e.getMessage()))
                .build();

            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultObject, httpStatus);
    }

    /**
     * Update input field
     * @param listRequests
     * @param listMassages
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<ResultObject> update(List<InputFieldUpdateRequest> listRequests, List<String> listMassages) {
        ResultObject resultObject;
        HttpStatus httpStatus;
        boolean updateSuccessful = false;
        List<String> messages = listMassages;
        List<InputField> inputFieldsUpdate = new ArrayList<>();
        List<InputFieldResponse> inputFieldResponses;

        try{
            if(listRequests.isEmpty()){
                throw new ApiValidateException("Failed: All item is not correct!");
            }
            for(InputFieldUpdateRequest updateRequest : listRequests){
                InputField inputField;

                if(!InputValidator.isLong(updateRequest.getId())){
                    messages.add("Failed: Invalid input field ID: " + updateRequest.getId());
                    continue;
                }
                if(!InputValidator.isLong(updateRequest.getStepId()) && updateRequest.getStepId() != null){
                    messages.add("Failed: Invalid step ID: " + updateRequest.getStepId());
                    continue;
                }

                if(!InputValidator.validateInputType(updateRequest.getType()) && updateRequest.getType() != null){
                    messages.add("Failed: Invalid input type: " + updateRequest.getType());
                    continue;
                }

                if(!InputValidator.validateInputPermission(updateRequest.getPermission()) && updateRequest.getPermission() != null){
                    messages.add("Failed: Invalid input permission: " + updateRequest.getPermission());
                    continue;
                }

                Optional<InputField> optionalInputField = inputFieldRepository.findById(Long.parseLong(updateRequest.getId()));
                if(!optionalInputField.isPresent()){
                    messages.add("Failed: Input field not found");
                    continue;
                } else {
                    inputField = optionalInputField.get();
                }

                if(updateRequest.getLabel() != null){
                    inputField.setLabel(updateRequest.getLabel());
                }

                if(updateRequest.getType() != null){
                    inputField.setType(EInputType.valueOf(updateRequest.getType()));
                }

                if(updateRequest.getPermission() != null){
                    inputField.setPermission(EInputPermission.valueOf(updateRequest.getPermission()));
                }

                if(updateRequest.getStepId() != null){
                    Optional<EvaluationStep> optionalStep = evaluationStepRepository.findById(Long.parseLong(updateRequest.getStepId()));
                    if(!optionalStep.isPresent()){
                        messages.add("Failed: Step id " + updateRequest.getStepId() + " is not found");
                        continue;
                    } else {
                        EvaluationStep step = optionalStep.get();
                        inputField.setEvaluationStep(step);
                    }
                }

                inputFieldRepository.save(inputField);
                messages.add("Successfully: updated!");
                inputFieldsUpdate.add(inputField);
                updateSuccessful = true;
            }
            if(updateSuccessful){
                inputFieldResponses = new InputFieldResponse().convertToListDTO(inputFieldsUpdate);
                messages.addFirst("Number of records updated: " + inputFieldResponses.size());

                resultObject = ResultObject.builder()
                    .status(HttpStatus.OK.name())
                    .messages(messages)
                    .data(inputFieldResponses)
                    .build();

                httpStatus = HttpStatus.OK;
            } else {
                throw new ApiValidateException("Failed: All item is not correct!");
            }
        } catch (ApiValidateException e){
            AppConstants.ERROR_LOGGER.error(e.getMessage());

            resultObject = ResultObject.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .messages(messages)
                .build();

            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());

            resultObject = ResultObject.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .messages(Collections.singletonList(e.getMessage()))
                .build();

            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(resultObject, httpStatus);
    }

}
