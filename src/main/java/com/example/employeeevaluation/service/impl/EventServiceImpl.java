package com.example.employeeevaluation.service.impl;

import com.example.employeeevaluation.dto.request.EventCreateRequest;
import com.example.employeeevaluation.dto.request.EventUpdateRequest;
import com.example.employeeevaluation.entity.*;
import com.example.employeeevaluation.exceptionhandler.customerException.ApiValidateException;
import com.example.employeeevaluation.repository.*;
import com.example.employeeevaluation.service.IEventService;
import com.example.employeeevaluation.util.AppConstants;
import com.example.employeeevaluation.util.ResultObject;
import java.time.LocalDate;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements IEventService {
  private final CompanyRepository companyRepository;
  private final EventCategoryRepository eventCategoryRepository;
  private final EventRepository eventRepository;
  private final EmployeeRepository employeeRepository;
  private final EventEmployeeRepository eventEmployeeRepository;

  public EventServiceImpl(
      CompanyRepository companyRepository,
      EventCategoryRepository eventCategoryRepository,
      EventRepository eventRepository,
      EmployeeRepository employeeRepository,
      EventEmployeeRepository eventEmployeeRepository) {
    this.companyRepository = companyRepository;
    this.eventCategoryRepository = eventCategoryRepository;
    this.eventRepository = eventRepository;
    this.employeeRepository = employeeRepository;
    this.eventEmployeeRepository = eventEmployeeRepository;
  }

  @Override
  public ResultObject createEvent(EventCreateRequest createRequest, Long companyId) {
    try {
      Company company =
          companyRepository
              .findById(companyId)
              .orElseThrow(() -> new ApiValidateException("Company Not Found"));

      EventCategory category =
          eventCategoryRepository
              .findById(createRequest.getEventCategoryId())
              .orElseThrow(() -> new ApiValidateException("Event Category Not Found"));

      if (createRequest.getStartDate().isAfter(createRequest.getEndDate())) {
        throw new ApiValidateException("Start date cannot be after end date");
      }

      Event event =
          Event.builder()
              .eventName(createRequest.getEventName())
              .startDate(createRequest.getStartDate())
              .endDate(createRequest.getEndDate())
              .company(company)
              .status(createRequest.getStatus())
              .categories(new HashSet<>(Collections.singleton(category)))
              .build();

      Event savedEvent = eventRepository.save(event);

      return ResultObject.builder()
          .status("OK")
          .messages(Collections.singletonList("Event created successfully"))
          .data(savedEvent)
          .build();
    } catch (ApiValidateException e) {
      AppConstants.ERROR_LOGGER.error(e.getMessage());
      return ResultObject.builder().status("ERROR").messages(Collections.singletonList(e.getMessage())).build();
    } catch (Exception e) {
      AppConstants.ERROR_LOGGER.error(e.getMessage());
      return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
    }
  }

  @Override
  public ResultObject updateEvent(Long id, EventUpdateRequest updateRequest) {
    try {
      Event event =
          eventRepository
              .findById(id)
              .orElseThrow(() -> new ApiValidateException("Event Not Found"));

      event.setEventName(updateRequest.getEventName());
      event.setStartDate(updateRequest.getStartDate());
      event.setEndDate(updateRequest.getEndDate());
      event.setStatus(updateRequest.getStatus());

      Company company =
          companyRepository
              .findById(updateRequest.getCompanyId())
              .orElseThrow(() -> new ApiValidateException("Company Not Found"));

      event.setCompany(company);

      Set<EventCategory> categories = new HashSet<>();
      for (Long categoryId : updateRequest.getEventCategoryIds()) {
        EventCategory category =
            eventCategoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ApiValidateException("Event Category Not Found"));
        categories.add(category);
      }
      event.setCategories(categories);

      Event updatedEvent = eventRepository.save(event);

      return ResultObject.builder()
          .status("OK")
          .messages(Collections.singletonList("Event updated successfully"))
          .data(updatedEvent)
          .build();
    } catch (ApiValidateException e) {
      AppConstants.ERROR_LOGGER.error(e.getMessage());
      return ResultObject.builder().status("ERROR").messages(Collections.singletonList(e.getMessage())).build();
    } catch (Exception e) {
      AppConstants.ERROR_LOGGER.error(e.getMessage());
      return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
    }
  }

  @Override
  public ResultObject deleteEvent(Long id) {
    try {
      Event event =
          eventRepository
              .findById(id)
              .orElseThrow(() -> new ApiValidateException("Event Not Found"));

      eventRepository.delete(event);

      return ResultObject.builder().status("OK").messages(Collections.singletonList("Event deleted successfully")).build();
    } catch (ApiValidateException e) {
      AppConstants.ERROR_LOGGER.error(e.getMessage());
      return ResultObject.builder().status("ERROR").messages(Collections.singletonList(e.getMessage())).build();
    } catch (Exception e) {
      AppConstants.ERROR_LOGGER.error(e.getMessage());
      return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
    }
  }

  @Override
  public ResultObject getEventById(Long id) {
    try {
      Event event =
          eventRepository
              .findById(id)
              .orElseThrow(() -> new ApiValidateException("Event Not Found"));

      return ResultObject.builder()
          .status("OK")
          .messages(Collections.singletonList("Event retrieved successfully"))
          .data(event)
          .build();
    } catch (ApiValidateException e) {
      AppConstants.ERROR_LOGGER.error(e.getMessage());
      return ResultObject.builder().status("ERROR").messages(Collections.singletonList(e.getMessage())).build();
    } catch (Exception e) {
      AppConstants.ERROR_LOGGER.error(e.getMessage());
      return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
    }
  }

  @Override
  public ResultObject getAllEvents() {
    try {
      List<Event> events = eventRepository.findAll();

      return ResultObject.builder()
          .status("OK")
          .messages(Collections.singletonList("Events retrieved successfully"))
          .data(events)
          .build();
    } catch (Exception e) {
      AppConstants.ERROR_LOGGER.error(e.getMessage());
      return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
    }
  }

  @Override
  public ResultObject addEmployeeToEvent(Long eventId, Long employeeId) {
    try {
      Employee employee =
          employeeRepository
              .findById(employeeId)
              .orElseThrow(() -> new ApiValidateException("Employee Not Found"));
      Event event =
          eventRepository
              .findById(eventId)
              .orElseThrow(() -> new ApiValidateException("Event Not Found"));
      EventEmployee eventEmployee =
          EventEmployee.builder()
              .event(event)
              .employee(employee)
              .addedDate(LocalDate.now())
              .build();
      EventEmployee savedEventEmployee = eventEmployeeRepository.save(eventEmployee);
      return ResultObject.builder()
          .status("OK")
          .messages(Collections.singletonList("Employee added to event successfully"))
          .data(savedEventEmployee)
          .build();
    } catch (Exception e) {
      AppConstants.ERROR_LOGGER.error(e.getMessage());
      return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
    }
  }
}
