package com.example.employeeevaluation.dto.request.validator;

import com.example.employeeevaluation.dto.request.UserRequest;
import com.example.employeeevaluation.util.InputValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserRequestValidator implements ConstraintValidator<ValidUserRequest, UserRequest> {
  @Override
  public boolean isValid(UserRequest userRequest, ConstraintValidatorContext context) {
    boolean isValid = true;

    if (userRequest.getUserName() != null) {
      isValid &=
          userRequest.getUserName().length() >= 4 && userRequest.getUserName().length() <= 40;
    }

    if (userRequest.getFullName() != null) {
      isValid &=
          userRequest.getFullName().length() >= 4 && userRequest.getFullName().length() <= 40;
    }

    if (userRequest.getEmail() != null) {
      isValid &= userRequest.getEmail().matches(InputValidator.emailRegexPattern);
    }

    if (userRequest.getPassword() != null) {
      isValid &=
          userRequest.getPassword().length() >= 6 && userRequest.getPassword().length() <= 20;
    }

    return isValid;
  }
}
