package com.example.employeeevaluation.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@Data
@JsonPropertyOrder({
  "success", "message",
})
public class ApiResponse {

  //    @JsonIgnore
  //    private static final long serialVersionUID = 7702134516418120340L;

  @JsonProperty("success")
  private Boolean success;

  @JsonProperty("message")
  private String message;

  @JsonIgnore private HttpStatus status;

  @JsonProperty("data")
  private Object data;

  public ApiResponse() {}

  public ApiResponse(Boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public ApiResponse(Boolean success, String message, Object data) {
    this.success = success;
    this.message = message;
    this.data = data;
  }

  public ApiResponse(Boolean success, String message, HttpStatus httpStatus, Object data) {
    this.success = success;
    this.message = message;
    this.data = data;
    this.status = httpStatus;
  }

  public ApiResponse(Boolean success, String message, HttpStatus httpStatus) {
    this.success = success;
    this.message = message;
    this.status = httpStatus;
  }
}
