package com.example.employeeevaluation.dto.response;

import com.example.employeeevaluation.entity.InputField;
import com.example.employeeevaluation.entity.enums.EInputPermission;
import com.example.employeeevaluation.entity.enums.EInputType;
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
public class InputFieldResponse {
  private Long id;
  private String label;
  private EInputType type;
  private EInputPermission permission;
  private Long stepId;
  private Long userId;

  public InputFieldResponse convertToDTO(InputField inputField) {
    return new InputFieldResponse(
        inputField.getId(),
        inputField.getLabel(),
        inputField.getType(),
        inputField.getPermission(),
        inputField.getEvaluationStep().getStepId(),
        inputField.getUser().getUserId());
  }

  public List<InputFieldResponse> convertToListDTO(List<InputField> inputFields) {
    List<InputFieldResponse> responses = new ArrayList<>();
    for (InputField inputField : inputFields) {
      responses.add(convertToDTO(inputField));
    }
    return responses;
  }
}
