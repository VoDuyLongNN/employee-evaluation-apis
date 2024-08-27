package com.example.employeeevaluation.controller;

import com.example.employeeevaluation.dto.request.InputValueRequest;
import com.example.employeeevaluation.service.impl.InputValueServiceImpl;
import com.example.employeeevaluation.util.AppConstants;
import com.example.employeeevaluation.util.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/input-value")
public class InputValueController {
    @Autowired
    InputValueServiceImpl inputValueIService;

    @GetMapping("/add")
    public ResponseEntity<ResultObject> addInputValue(@RequestBody InputValueRequest request){
        AppConstants.INFOR_LOGGER.info("controller: Start add input value");

        ResultObject response = inputValueIService.addInputValue(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<ResultObject> getAllByUserId(){
        AppConstants.INFOR_LOGGER.info("controller: get all input event by user id");

        ResultObject response = inputValueIService.getInputValuesByUserId();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ResultObject> getAll(){
        AppConstants.INFOR_LOGGER.info("controller: get all input event");

        ResultObject response = inputValueIService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResultObject> delete(@PathVariable Long id){
        AppConstants.INFOR_LOGGER.info("controller: delete input event");
        ResultObject response = inputValueIService.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ResultObject> update(@RequestBody InputValueRequest request){
        AppConstants.INFOR_LOGGER.info("controller: update input event");

        ResultObject response = inputValueIService.update(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
