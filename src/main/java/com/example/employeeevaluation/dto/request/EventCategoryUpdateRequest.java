package com.example.employeeevaluation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data // toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventCategoryUpdateRequest {
  @NotBlank(message = "Category name is required")
  private String categoryName;

  private String description;
}
