package com.example.employeeevaluation.dto.request;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RequestObject {
  protected Map<String, Object> unknownFields = new HashMap<>();

  @JsonAnySetter
  public void setUnknownFields(String key, Object value) {
	unknownFields.put(key, value);
  }

}
