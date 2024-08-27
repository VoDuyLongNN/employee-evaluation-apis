package com.example.employeeevaluation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data // toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {
  @NotEmpty(message = "Company name cannot be empty")
  @JsonProperty("company_name")
  private String companyName;

  @JsonProperty("address")
  @NotEmpty(message = "Company address cannot be empty")
  private String address;

  @JsonProperty("description")
  private String description;
}
