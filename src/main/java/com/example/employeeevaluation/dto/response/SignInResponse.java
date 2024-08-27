package com.example.employeeevaluation.dto.response;

import com.example.employeeevaluation.entity.Role;
import java.util.Date;
import java.util.Set;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInResponse {
  private Long userId;
  private Set<Role> role;
  private String token;
  private String refreshToken;
  private Date expirationTime;
  private String error;
}
