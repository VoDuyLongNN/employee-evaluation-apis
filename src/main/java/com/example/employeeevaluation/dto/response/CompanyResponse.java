package com.example.employeeevaluation.dto.response;

import com.example.employeeevaluation.entity.EventCategory;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponse {
  private Long companyId;
  private String companyName;
  private String address;
  private List<EventCategory> eventCategories;
}
