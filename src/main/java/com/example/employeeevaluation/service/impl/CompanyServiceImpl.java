package com.example.employeeevaluation.service.impl;

import com.example.employeeevaluation.config.LocalizationUtils;
import com.example.employeeevaluation.dto.request.CompanyRequest;
import com.example.employeeevaluation.dto.response.ApiResponse;
import com.example.employeeevaluation.dto.response.PagedResponse;
import com.example.employeeevaluation.entity.Company;
import com.example.employeeevaluation.exceptionhandler.ResourceNotFoundException;
import com.example.employeeevaluation.repository.CompanyRepository;
import com.example.employeeevaluation.service.ICompanyService;
import com.example.employeeevaluation.util.AppConstants;
import com.example.employeeevaluation.util.AppUtils;
import com.example.employeeevaluation.util.MessageKeys;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CompanyServiceImpl implements ICompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final LocalizationUtils localizationUtils;

    /**
     * get all companies
     * @param page
     * @param size
     * @return
     */
    @Override
    public PagedResponse<Company> getAllCompanies(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, AppConstants.CREATED_AT);

        Page<Company> companies = companyRepository.findAll(pageable);

        List<Company> content = companies.getNumberOfElements() == 0 ? Collections.emptyList() : companies.getContent();

        return new PagedResponse<>(content, companies.getNumber(), companies.getSize(), companies.getTotalElements(),
                companies.getTotalPages(), companies.isLast());
    }

    /**
     * get company by id
     * @param id
     * @return
     */

    @Override
    public ResponseEntity<ApiResponse> getCompanyById(Long id) {
        String message = localizationUtils.getLocalizedMessage(MessageKeys.GET_COMPANY_SUCCESSFULLY);
        String messageNotFound = localizationUtils.getLocalizedMessage(MessageKeys.COMPANY_NOT_FOUND);
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageNotFound + id));
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, message, company) , HttpStatus.OK);
    }

    /**
     * add company
     * @param companyRequest
     * @return
     */

    @Override
    public ResponseEntity<ApiResponse> addCompany(CompanyRequest companyRequest) {
        try {
            Company company = modelMapper.map(companyRequest, Company.class);

            Company newCompany = companyRepository.save(company);
            String message = localizationUtils.getLocalizedMessage(MessageKeys.INSERT_COMPANY_SUCCESSFULLY);

            ApiResponse response = new ApiResponse(true, message, newCompany);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            AppConstants.ERROR_LOGGER.error(ex.getMessage());
            ApiResponse response = new ApiResponse(false, ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * update company
     * @param id
     * @param newCompany
     * @return
     */

    @Override
    public ResponseEntity<ApiResponse> updateCompany(Long id, CompanyRequest newCompany) {
        String message = localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_COMPANY_SUCCESSFULLY);
        String messageNotFound = localizationUtils.getLocalizedMessage(MessageKeys.COMPANY_NOT_FOUND);

        return companyRepository.findById(id)
                .map(company -> {
                    company.setCompanyName(newCompany.getCompanyName());
                    company.setAddress(newCompany.getAddress());
                    company.setDescription(newCompany.getDescription());
                    Company updatedCompany = companyRepository.save(company);
                    return ResponseEntity.ok().body(new ApiResponse(Boolean.TRUE, message));
                })
                .orElseThrow(() -> new ResourceNotFoundException(messageNotFound + id));
    }

    /**
     * delete company by id
     * @param id
     * @return
     */

    @Override
    public ResponseEntity<ApiResponse> deleteCompany(Long id) {
        String message = localizationUtils.getLocalizedMessage(MessageKeys.DELETE_COMPANY_SUCCESSFULLY);
        String messageNotFound = localizationUtils.getLocalizedMessage(MessageKeys.COMPANY_NOT_FOUND);

        return companyRepository.findById(id)
                .map(company -> {
                    companyRepository.delete(company);
                    return ResponseEntity.ok().body(new ApiResponse(Boolean.TRUE, message));
                })
                .orElseThrow(() -> new ResourceNotFoundException(messageNotFound + id));
    }
}
