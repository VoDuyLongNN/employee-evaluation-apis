package com.example.employeeevaluation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data // toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationStepUpdateRequest {
  @NotBlank(message = "Step name is required")
  private String stepName;

  @NotNull(message = "Event ID is required")
  private Long eventId;

  @NotNull(message = "Level is required")
  private int level;
}
