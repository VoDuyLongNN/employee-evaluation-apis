package com.example.employeeevaluation.dto.response;

import com.example.employeeevaluation.entity.InputValue;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InputValueResponse {
  private Long id;
  private Long userId;
  private Long inputFieldId;
  private String value;

  public InputValueResponse convertToDTO(InputValue inputValue) {
    return new InputValueResponse(
        inputValue.getId(),
        inputValue.getUser().getUserId(),
        inputValue.getInputField().getId(),
        inputValue.getValue());
  }

  public List<InputValueResponse> convertToListDTO(List<InputValue> inputValues) {
    List<InputValueResponse> list = new ArrayList<>();
    for (InputValue inputValue : inputValues) {
      list.add(convertToDTO(inputValue));
    }
    return list;
  }
}
