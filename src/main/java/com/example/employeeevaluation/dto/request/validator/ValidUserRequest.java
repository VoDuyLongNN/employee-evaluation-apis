package com.example.employeeevaluation.dto.request.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserRequestValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserRequest {
  String message() default "Invalid user request";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
