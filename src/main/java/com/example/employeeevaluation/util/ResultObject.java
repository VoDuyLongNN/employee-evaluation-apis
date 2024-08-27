package com.example.employeeevaluation.util;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResultObject {
  private String status;
  private List<String> messages;
  private Object data;
}
