package com.example.employeeevaluation.service.impl;

import com.example.employeeevaluation.dto.request.EventCategoryCreateRequest;
import com.example.employeeevaluation.dto.request.EventCategoryUpdateRequest;
import com.example.employeeevaluation.entity.Company;
import com.example.employeeevaluation.entity.EventCategory;
import com.example.employeeevaluation.exceptionhandler.customerException.ApiValidateException;
import com.example.employeeevaluation.repository.CompanyRepository;
import com.example.employeeevaluation.repository.EventCategoryRepository;
import com.example.employeeevaluation.service.IEventCategoryService;
import com.example.employeeevaluation.util.AppConstants;
import com.example.employeeevaluation.util.ResultObject;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EventCategoryServiceImpl implements IEventCategoryService {
    private final CompanyRepository companyRepository;
    private final EventCategoryRepository eventCategoryRepository;

    public EventCategoryServiceImpl(CompanyRepository companyRepository, EventCategoryRepository eventCategoryRepository) {
        this.companyRepository = companyRepository;
        this.eventCategoryRepository = eventCategoryRepository;
    }

    @Override
    public ResultObject createEventCategory(EventCategoryCreateRequest createRequest, Long companyId) {
        try {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new ApiValidateException("Company Not Found"));

            EventCategory eventCategory = EventCategory.builder()
                    .categoryName(createRequest.getCategoryName())
                    .description(createRequest.getDescription())
                    .company(company)
                    .build();

            EventCategory savedCategory = eventCategoryRepository.save(eventCategory);

            return ResultObject.builder().status("OK").messages(Collections.singletonList("Event Category created successfully")).data(savedCategory).build();
        } catch (ApiValidateException e) {
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList(e.getMessage())).build();
        } catch (Exception e) {
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
        }
    }

    @Override
    public ResultObject updateEventCategory(Long id, EventCategoryUpdateRequest updateRequest) {
        try {
            EventCategory category = eventCategoryRepository.findById(id)
                    .orElseThrow(() -> new ApiValidateException("Event Category Not Found"));

            category.setCategoryName(updateRequest.getCategoryName());
            category.setDescription(updateRequest.getDescription());

            EventCategory updatedCategory = eventCategoryRepository.save(category);

            return ResultObject.builder().status("OK").messages(Collections.singletonList("Event Category updated successfully")).data(updatedCategory).build();
        } catch (ApiValidateException e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList(e.getMessage())).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
        }
    }

    @Override
    public ResultObject deleteEventCategory(Long id) {
        try {
            EventCategory category = eventCategoryRepository.findById(id)
                    .orElseThrow(() -> new ApiValidateException("Event Category Not Found"));

            eventCategoryRepository.delete(category);

            return ResultObject.builder().status("OK").messages(Collections.singletonList("Event Category deleted successfully")).build();
        } catch (ApiValidateException e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList(e.getMessage())).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
        }
    }

    @Override
    public ResultObject getEventCategoryById(Long id) {
        try {
            EventCategory category = eventCategoryRepository.findById(id)
                    .orElseThrow(() -> new ApiValidateException("Event Category Not Found"));

            return ResultObject.builder().status("OK").messages(Collections.singletonList("Event Category retrieved successfully")).data(category).build();
        } catch (ApiValidateException e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList(e.getMessage())).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
        }
    }

    @Override
    public ResultObject getAllEventCategories() {
        try {
            List<EventCategory> categories = eventCategoryRepository.findAll();

            return ResultObject.builder().status("OK").messages(Collections.singletonList("Event Categories retrieved successfully")).data(categories).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            return ResultObject.builder().status("ERROR").messages(Collections.singletonList("An unexpected error occurred")).build();
        }
    }
}
