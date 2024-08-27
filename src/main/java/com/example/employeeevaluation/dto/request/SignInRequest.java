package com.example.employeeevaluation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest extends RequestObject {
  @NotEmpty(message = "Username name cannot be empty")
  private String userName;

  @NotEmpty(message = "Password name cannot be empty")
  private String password;

}
