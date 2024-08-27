package com.example.employeeevaluation.dto.request;

import com.example.employeeevaluation.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventCategoryRequest {
  private Long evtCategoryId;
  private String categoryName;
  private String description;
  private Company company;
}
