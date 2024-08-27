package com.example.employeeevaluation.dto.request;

import com.example.employeeevaluation.dto.request.validator.ValidUserRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@ValidUserRequest
@SuppressWarnings("unused")
public class UserRequest {
  private String userName;
  private String fullName;
  private String email;
  private String password;
  private Long companyId;
}
