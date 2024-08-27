package com.example.employeeevaluation.dto.request;

import com.example.employeeevaluation.entity.enums.EStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventCreateRequest {
  @NotNull(message = "Event name is required")
  private String eventName;

  @NotNull(message = "Start date is required")
  private LocalDate startDate;

  @NotNull(message = "End date is required")
  private LocalDate endDate;

  @NotNull(message = "Event category ID is required")
  private Long eventCategoryId;

  @NotBlank(message = "Status is required")
  private EStatus status;
}
