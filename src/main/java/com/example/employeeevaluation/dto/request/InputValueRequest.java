package com.example.employeeevaluation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InputValueRequest {
  private Long inputValueId;
  private Long inputFieldId;
  private String value;
}
