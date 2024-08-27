package com.example.employeeevaluation.controller;

import com.example.employeeevaluation.dto.request.EmployeeEventRequest;
import com.example.employeeevaluation.dto.request.EventCreateRequest;
import com.example.employeeevaluation.dto.request.EventUpdateRequest;
import com.example.employeeevaluation.entity.User;
import com.example.employeeevaluation.exceptionhandler.customerException.ApiValidateException;
import com.example.employeeevaluation.service.IEventService;
import com.example.employeeevaluation.util.AppConstants;
import com.example.employeeevaluation.util.ResultObject;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.prefix}/event")
public class EventController {

    private final IEventService eventService;

    @Autowired
    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResultObject> create(@RequestBody EventCreateRequest createRequest) {
        AppConstants.INFOR_LOGGER.info("Controller: create event");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long companyId = user.getCompany().getCompanyId();
        ResultObject eventByCompany = eventService.createEvent(createRequest, companyId);
        return new ResponseEntity<>(eventByCompany, HttpStatus.OK);
    }

    @PostMapping("/{eventId}/add-employee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResultObject> addEmployeeToEvent(@PathVariable @RequestBody Long eventId, @RequestBody EmployeeEventRequest employeeId) {
        AppConstants.INFOR_LOGGER.info("Controller: add employee to event");
        ResultObject resultObject = eventService.addEmployeeToEvent(eventId, employeeId.getEmployeeId());
        return new ResponseEntity<>(resultObject, HttpStatus.OK);
    }

    @PostMapping("/{companyId}/add-new")
    public ResponseEntity<ResultObject> createEvent(@RequestBody @Valid EventCreateRequest eventCreateRequest,
                                                    @PathVariable Long companyId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApiValidateException("Data Binding Has Error");
        }
        ResultObject resultObject = eventService.createEvent(eventCreateRequest, companyId);
        return new ResponseEntity<>(resultObject, HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ResultObject> updateEvent(@PathVariable Long id,
                                                    @RequestBody @Valid EventUpdateRequest eventUpdateRequest,
                                                    BindingResult bindingResult) {
        AppConstants.INFOR_LOGGER.info("Controller: update event");
        if (bindingResult.hasErrors()) {
            throw new ApiValidateException("Data Binding Has Error");
        }
        ResultObject resultObject = eventService.updateEvent(id, eventUpdateRequest);
        return new ResponseEntity<>(resultObject, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ResultObject> deleteEvent(@PathVariable Long id) {
        AppConstants.INFOR_LOGGER.info("Controller: delete event");
        ResultObject resultObject = eventService.deleteEvent(id);
        return new ResponseEntity<>(resultObject, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultObject> getEventById(@PathVariable Long id) {
        AppConstants.INFOR_LOGGER.info("Controller: get event by id");
        ResultObject resultObject = eventService.getEventById(id);
        return new ResponseEntity<>(resultObject, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ResultObject> getAllEvents() {
        AppConstants.INFOR_LOGGER.info("Controller: get all event");
        ResultObject resultObject = eventService.getAllEvents();
        return new ResponseEntity<>(resultObject, HttpStatus.OK);
    }
}
