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
public class SignOutRequest extends RequestObject{
  @NotEmpty(message = "Token cannot blank")
  private String token;
}
