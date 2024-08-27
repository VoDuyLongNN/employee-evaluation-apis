package com.example.employeeevaluation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ChangePasswordRequest {
  @NotBlank(message = "Old password must not be blank")
  @Size(min = 6, max = 20, message = "New password must be between 6 and 20 characters")
  private String oldPassword;

  @NotBlank(message = "New password must not be blank")
  @Size(min = 6, max = 20, message = "New password must be between 6 and 20 characters")
  private String newPassword;
}
