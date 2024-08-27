package com.example.employeeevaluation.service;

import com.example.employeeevaluation.dto.request.InputValueRequest;
import com.example.employeeevaluation.util.ResultObject;

public interface IInputValueService {
    ResultObject addInputValue(InputValueRequest inputValueRequest);

    ResultObject getInputValuesByUserId();

    ResultObject getAll();

    ResultObject deleteById(Long id);

    ResultObject update(InputValueRequest request);
}
