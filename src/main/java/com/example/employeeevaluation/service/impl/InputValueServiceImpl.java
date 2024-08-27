package com.example.employeeevaluation.service.impl;

import com.example.employeeevaluation.config.LocalizationUtils;
import com.example.employeeevaluation.dto.request.InputValueRequest;
import com.example.employeeevaluation.dto.response.InputValueResponse;
import com.example.employeeevaluation.entity.InputField;
import com.example.employeeevaluation.entity.InputValue;
import com.example.employeeevaluation.entity.User;
import com.example.employeeevaluation.exceptionhandler.customerException.ApiValidateException;
import com.example.employeeevaluation.repository.InputFieldRepository;
import com.example.employeeevaluation.repository.InputValueRepository;
import com.example.employeeevaluation.repository.UserRepository;
import com.example.employeeevaluation.service.IInputValueService;
import com.example.employeeevaluation.util.AppConstants;
import com.example.employeeevaluation.util.MessageKeys;
import com.example.employeeevaluation.util.ResultObject;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class InputValueServiceImpl implements IInputValueService {
    @Autowired
    private InputValueRepository inputValueRepository;

    @Autowired
    private InputFieldRepository inputFieldRepository;

    @Autowired
    private LocalizationUtils localizationUtils;

    @Override
    public ResultObject addInputValue(InputValueRequest request) {
        InputValue inputValue;
        InputValueResponse inputValueResponse;
        ResultObject resultObject;
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            InputField inputField = inputFieldRepository.findById(request.getInputFieldId()).orElseThrow(() -> new ApiValidateException("Input field not found!"));

            inputValue = InputValue.builder()
                    .user(user)
                    .inputField(inputField)
                    .value(request.getValue())
                    .build();

            inputValueRepository.save(inputValue);

            inputValueResponse = new InputValueResponse().convertToDTO(inputValue);

            resultObject = ResultObject.builder()
                    .status(HttpStatus.OK.name())
                    .messages(Collections.singletonList(localizationUtils.getLocalizedMessage(MessageKeys.ADD_INPUT_VALUE)))
                    .data(inputValueResponse)
                    .build();
        } catch (Exception e) {
            resultObject = ResultObject.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .messages(Collections.singletonList(e.getMessage()))
                    .build();
        }
        return resultObject;
    }

    @Override
    public ResultObject getInputValuesByUserId() {
        ResultObject resultObject;
        List<InputValue> inputValueList;
        List<InputValueResponse> dataResponse;

        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            inputValueList = inputValueRepository.getAllByUser(user);
            dataResponse = new InputValueResponse().convertToListDTO(inputValueList);
            resultObject = ResultObject.builder()
                    .status(HttpStatus.OK.name())
                    .messages(Collections.singletonList(localizationUtils.getLocalizedMessage(MessageKeys.GET_INPUT_VALUE_BY_USER_ID)))
                    .data(dataResponse)
                    .build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            resultObject = ResultObject.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .messages(Collections.singletonList(e.getMessage()))
                    .build();
        }

        return resultObject;
    }

    @Override
    public ResultObject getAll() {
        ResultObject resultObject;
        List<InputValue> inputValueList;
        List<InputValueResponse> dataResponse;

        try{
            inputValueList = inputValueRepository.findAll();
            dataResponse = new InputValueResponse().convertToListDTO(inputValueList);

            resultObject = ResultObject.builder()
                    .status(HttpStatus.OK.name())
                    .messages(Collections.singletonList(localizationUtils.getLocalizedMessage(MessageKeys.GET_ALL_INPUT_VALUES)))
                    .data(dataResponse)
                    .build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            resultObject = ResultObject.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .messages(Collections.singletonList(e.getMessage()))
                    .build();
        }
        return resultObject;
    }

    @Override
    public ResultObject deleteById(Long id){
        ResultObject resultObject;

        try{
            InputValue inputValue = inputValueRepository.findById(id).orElseThrow(() -> new ApiValidateException("Input value not found!"));
            inputValueRepository.delete(inputValue);

            resultObject = ResultObject.builder()
                    .status(HttpStatus.OK.name())
                    .messages(Collections.singletonList(localizationUtils.getLocalizedMessage(MessageKeys.DELETE_INPUT_VALUE)))
                    .build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            resultObject = ResultObject.builder()
                    .status(HttpStatus.INSUFFICIENT_STORAGE.name())
                    .messages(Collections.singletonList(e.getMessage()))
                    .build();
        }
        return resultObject;
    }

    @Override
    public ResultObject update(InputValueRequest request) {
        ResultObject resultObject;
        InputValue inputValue;
        InputValueResponse inputValueResponse;

        try{
            inputValue = inputValueRepository.findById(request.getInputValueId()).orElseThrow(() -> new ApiValidateException("Input value not found!"));
            if(request.getInputFieldId() != null){
                InputField inputField = inputFieldRepository.findById(request.getInputFieldId()).orElseThrow(() -> new ApiValidateException("Input field not found!"));
                inputValue.setInputField(inputField);
            }
            if(request.getValue() != null){
                inputValue.setValue(request.getValue());
            }

            inputValueRepository.save(inputValue);

            inputValueResponse = new InputValueResponse().convertToDTO(inputValue);
            resultObject = ResultObject.builder()
                    .status(HttpStatus.OK.name())
                    .messages(Collections.singletonList(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_INPUT_VALUE)))
                    .data(inputValueResponse)
                    .build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            resultObject = ResultObject.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .messages(Collections.singletonList(e.getMessage()))
                    .build();
        }

        return resultObject;
    }
}
