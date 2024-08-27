package com.example.employeeevaluation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class InputFieldRequest extends RequestObject{
  @NotEmpty(message = "message can not be null!")
  private String label;

  @NotEmpty(message = "type can not be null!")
  private String type;

  @NotEmpty(message = "Permission can not be null!")
  private String permission;

  @NotNull(message = "Step id can not be null!")
  private String stepId;
}
