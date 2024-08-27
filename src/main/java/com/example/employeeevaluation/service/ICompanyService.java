package com.example.employeeevaluation.service;

import com.example.employeeevaluation.dto.request.CompanyRequest;
import com.example.employeeevaluation.dto.response.ApiResponse;
import com.example.employeeevaluation.dto.response.PagedResponse;
import com.example.employeeevaluation.entity.Company;
import org.springframework.http.ResponseEntity;

public interface ICompanyService {
    PagedResponse<Company> getAllCompanies(int page, int size);

    ResponseEntity<ApiResponse> getCompanyById(Long id);

    ResponseEntity<ApiResponse> addCompany(CompanyRequest companyRequest);

    ResponseEntity<ApiResponse> updateCompany(Long id, CompanyRequest newCompany);

    ResponseEntity<ApiResponse> deleteCompany(Long id);
}
