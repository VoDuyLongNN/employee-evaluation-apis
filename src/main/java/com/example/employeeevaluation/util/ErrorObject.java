package com.example.employeeevaluation.util;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorObject {
  private int statusCode;
  private String message;
  private String description;
}
