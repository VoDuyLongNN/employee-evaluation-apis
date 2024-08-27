package com.example.employeeevaluation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventCategoryCreateRequest {
  @NotBlank(message = "Category name is required")
  private String categoryName;

  private String description;

  @NotNull(message = "Company ID is required")
  private Long companyId;
}
