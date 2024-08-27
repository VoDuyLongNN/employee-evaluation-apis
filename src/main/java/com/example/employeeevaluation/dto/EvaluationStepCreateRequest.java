package com.example.employeeevaluation.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationStepCreateRequest {
  private String stepName;
  private Long eventId;
  private int level;
}
