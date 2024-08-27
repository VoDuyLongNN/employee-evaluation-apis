package com.example.employeeevaluation.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class InputFieldUpdateRequest extends RequestObject{
  private String id;

  private String label;

  private String type;

  private String permission;

  private String stepId;
}
