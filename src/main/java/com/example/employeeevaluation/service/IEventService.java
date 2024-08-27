package com.example.employeeevaluation.service;

import com.example.employeeevaluation.dto.request.EventCreateRequest;
import com.example.employeeevaluation.dto.request.EventUpdateRequest;
import com.example.employeeevaluation.util.ResultObject;

public interface IEventService {
  ResultObject createEvent(EventCreateRequest createRequest, Long companyId);

  ResultObject updateEvent(Long id, EventUpdateRequest updateRequest);

  ResultObject deleteEvent(Long id);

  ResultObject getEventById(Long id);

  ResultObject getAllEvents();

  ResultObject addEmployeeToEvent(Long eventId, Long employeeId);
}
