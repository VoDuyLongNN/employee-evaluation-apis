package com.example.employeeevaluation.controller;

import com.example.employeeevaluation.dto.request.EventCategoryCreateRequest;
import com.example.employeeevaluation.exceptionhandler.customerException.ApiValidateException;
import com.example.employeeevaluation.service.IEventCategoryService;
import com.example.employeeevaluation.util.ResultObject;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/event-category")
public class EventCategoryRestController {

  private final IEventCategoryService eventCategoryService;

  public EventCategoryRestController(IEventCategoryService eventCategoryService) {
    this.eventCategoryService = eventCategoryService;
  }

  @PostMapping("/{companyId}/add-new-category")
  public ResponseEntity<ResultObject> createEventCategory(
      @RequestBody @Valid EventCategoryCreateRequest eventCategoryCreateRequest,
      @PathVariable Long companyId,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new ApiValidateException("Data Binding Has Error");
    }
    ResultObject resultObject =
        eventCategoryService.createEventCategory(eventCategoryCreateRequest, companyId);
    return new ResponseEntity<>(resultObject, HttpStatus.OK);
  }
}
