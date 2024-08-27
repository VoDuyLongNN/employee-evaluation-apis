package com.example.employeeevaluation.controller;

import com.example.employeeevaluation.dto.request.SignInRequest;
import com.example.employeeevaluation.dto.request.SignOutRequest;
import com.example.employeeevaluation.dto.request.UserRequest;
import com.example.employeeevaluation.exceptionhandler.customerException.ApiValidateException;
import com.example.employeeevaluation.service.IUserService;
import com.example.employeeevaluation.util.AppConstants;
import com.example.employeeevaluation.util.ResultObject;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/${api.prefix}/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResultObject> registerUser(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        AppConstants.INFOR_LOGGER.info("Controller: signup");

        if (bindingResult.hasErrors()) {
            throw new ApiValidateException("Validation Failed: " + bindingResult.getAllErrors());
        }
        ResultObject response = userService.registerUser(userRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<ResultObject> signin(@Valid @RequestBody SignInRequest signInRequest, BindingResult result) {
        AppConstants.INFOR_LOGGER.info("Controller: signin");
        if (result.hasErrors()) {
            List<String> error = result.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
            ResultObject errorResponse = ResultObject.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .messages(error)
                .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        if(!signInRequest.getUnknownFields().isEmpty()){
            List<String> unknownFieldsMessage = signInRequest.getUnknownFields().keySet().stream()
                .map(field -> "Unknown field: " + field)
                .collect(Collectors.toList());
            ResultObject errorResponse = ResultObject.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .messages(unknownFieldsMessage)
                .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        return userService.signIn(signInRequest);
    }

    @PostMapping("/signout")
    public ResponseEntity<ResultObject> signout(@Valid @RequestBody SignOutRequest signOutRequest, BindingResult result) {
        AppConstants.INFOR_LOGGER.info("Controller: signout");
        if (result.hasErrors()) {
            List<String> error = result.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
            ResultObject errorResponse = ResultObject.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .messages(error)
                .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        if(!signOutRequest.getUnknownFields().isEmpty()){
            List<String> unknownFieldsMessage = signOutRequest.getUnknownFields().keySet().stream()
                .map(field -> "Unknown field: " + field)
                .collect(Collectors.toList());
            ResultObject errorResponse = ResultObject.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .messages(unknownFieldsMessage)
                .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        return userService.signOut(signOutRequest);
    }

}
