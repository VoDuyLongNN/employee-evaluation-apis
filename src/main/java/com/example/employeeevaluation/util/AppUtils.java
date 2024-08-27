package com.example.employeeevaluation.util;

import com.example.employeeevaluation.exceptionhandler.EventEvaluationApiException;
import org.springframework.http.HttpStatus;

public class AppUtils {
  public static void validatePageNumberAndSize(int page, int size) {
    if (page < 0) {
      throw new EventEvaluationApiException(
          HttpStatus.BAD_REQUEST, "Page number cannot be less than zero.");
    }

    if (size < 0) {
      throw new EventEvaluationApiException(
          HttpStatus.BAD_REQUEST, "Size number cannot be less than zero.");
    }

    if (size > AppConstants.PAGING_MAX_PAGE_SIZE) {
      throw new EventEvaluationApiException(
          HttpStatus.BAD_REQUEST,
          "Page size must not be greater than " + AppConstants.PAGING_MAX_PAGE_SIZE);
    }
  }
}
