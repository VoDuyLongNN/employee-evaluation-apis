package com.example.employeeevaluation.service;

import com.example.employeeevaluation.dto.request.EventCategoryCreateRequest;
import com.example.employeeevaluation.dto.request.EventCategoryUpdateRequest;
import com.example.employeeevaluation.util.ResultObject;

public interface IEventCategoryService {
    ResultObject createEventCategory(EventCategoryCreateRequest createRequest, Long companyId);
    ResultObject updateEventCategory(Long id, EventCategoryUpdateRequest updateRequest);
    ResultObject deleteEventCategory(Long id);
    ResultObject getEventCategoryById(Long id);
    ResultObject getAllEventCategories();
}
