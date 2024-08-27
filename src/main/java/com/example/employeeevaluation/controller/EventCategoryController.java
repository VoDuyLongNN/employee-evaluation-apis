package com.example.employeeevaluation.controller;

import com.example.employeeevaluation.dto.request.EventCategoryCreateRequest;
import com.example.employeeevaluation.dto.request.EventCategoryUpdateRequest;
import com.example.employeeevaluation.exceptionhandler.customerException.ApiValidateException;
import com.example.employeeevaluation.service.IEventCategoryService;
import com.example.employeeevaluation.util.AppConstants;
import com.example.employeeevaluation.util.ResultObject;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/event-category")
public class EventCategoryController {
  public IEventCategoryService eventCategoryService;

  @Autowired
  public EventCategoryController(IEventCategoryService eventCategoryService) {
    this.eventCategoryService = eventCategoryService;
  }

  @PostMapping("/{companyId}/add-new")
  public ResponseEntity<ResultObject> createEventCategory(
      @RequestBody @Valid EventCategoryCreateRequest eventCategoryCreateRequest,
      @PathVariable Long companyId,
      BindingResult bindingResult) {
    AppConstants.INFOR_LOGGER.info("Controller: create event category");
    if (bindingResult.hasErrors()) {
      throw new ApiValidateException("Data Binding Has Error");
    }
    ResultObject resultObject =
        eventCategoryService.createEventCategory(eventCategoryCreateRequest, companyId);
    return new ResponseEntity<>(resultObject, HttpStatus.OK);
  }

  /*
  @PutMapping("/{id}/update")
  public ResponseEntity<ResultObject> updateEventCategory(@PathVariable Long id,
                                                          @RequestBody @Valid EventCategoryUpdateRequest updateRequest,
                                                          BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
          throw new ApiValidateException("Data Binding Has Error");
      }
      ResultObject resultObject = eventCategoryService.updateEventCategory(id, updateRequest);
      return new ResponseEntity<>(resultObject, HttpStatus.OK);
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<ResultObject> deleteEventCategory(@PathVariable Long id) {
      ResultObject resultObject = eventCategoryService.deleteEventCategory(id);
      return new ResponseEntity<>(resultObject, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResultObject> getEventCategoryById(@PathVariable Long id) {
      ResultObject resultObject = eventCategoryService.getEventCategoryById(id);
      return new ResponseEntity<>(resultObject, HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<ResultObject> getAllEventCategories() {
      ResultObject resultObject = eventCategoryService.getAllEventCategories();
      return new ResponseEntity<>(resultObject, HttpStatus.OK);
  }
   */
  @PutMapping("/{id}/update")
  public ResponseEntity<ResultObject> updateEventCategory(
      @PathVariable Long id,
      @RequestBody @Valid EventCategoryUpdateRequest eventCategoryUpdateRequest,
      BindingResult bindingResult) {
    AppConstants.INFOR_LOGGER.info("Controller: update event category");
    if (bindingResult.hasErrors()) {
      throw new ApiValidateException("Data Binding Has Error");
    }
    ResultObject resultObject =
        eventCategoryService.updateEventCategory(id, eventCategoryUpdateRequest);
    return new ResponseEntity<>(resultObject, HttpStatus.OK);
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<ResultObject> deleteEventCategory(@PathVariable Long id) {
    AppConstants.INFOR_LOGGER.info("Controller: delete event category");
    ResultObject resultObject = eventCategoryService.deleteEventCategory(id);
    return new ResponseEntity<>(resultObject, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResultObject> getEventCategoryById(@PathVariable Long id) {
    AppConstants.INFOR_LOGGER.info("Controller: get event category by id");
    ResultObject resultObject = eventCategoryService.getEventCategoryById(id);
    return new ResponseEntity<>(resultObject, HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<ResultObject> getAllEventCategories() {
    AppConstants.INFOR_LOGGER.info("Controller: get all event category");
    ResultObject resultObject = eventCategoryService.getAllEventCategories();
    return new ResponseEntity<>(resultObject, HttpStatus.OK);
  }
}
