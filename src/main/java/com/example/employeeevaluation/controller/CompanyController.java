package com.example.employeeevaluation.controller;

import com.example.employeeevaluation.dto.request.CompanyRequest;
import com.example.employeeevaluation.dto.response.ApiResponse;
import com.example.employeeevaluation.dto.response.PagedResponse;
import com.example.employeeevaluation.entity.Company;
import com.example.employeeevaluation.service.ICompanyService;
import com.example.employeeevaluation.util.AppConstants;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/companies")
public class CompanyController {
    @Autowired
    private ICompanyService companyService;

    /**
     * get all companies
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public PagedResponse<Company> getAllCompanies(
            @RequestParam(name = "page", required = false, defaultValue = AppConstants.PAGING_DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = AppConstants.PAGING_DEFAULT_PAGE_SIZE) Integer size) {
        AppConstants.INFOR_LOGGER.info("Controller: get all companies");
        return companyService.getAllCompanies(page, size);
    }

    /**
     * add new company
     * @param companyRequest
     * @param result
     * @return
     */
    @PostMapping
    public ResponseEntity<?> addCompany(@Valid @RequestBody CompanyRequest companyRequest,
                                        BindingResult result
    ) {
        AppConstants.INFOR_LOGGER.info("Controller: add company");
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return companyService.addCompany(companyRequest);
        } catch (Exception e) {
            AppConstants.INFOR_LOGGER.info("controller: get all input event");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * get company by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable(name = "id") Long id) {
        AppConstants.INFOR_LOGGER.info("Controller: get company by id");
        return companyService.getCompanyById(id);
    }

    /**
     * update company
     * @param id
     * @param company
     * @param result
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable(name = "id") Long id,
                                           @Valid @RequestBody CompanyRequest company,
                                           BindingResult result) {
        AppConstants.INFOR_LOGGER.info("Controller: update company");
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return companyService.updateCompany(id, company);

    }

    /**
     * delete company by id
     * @param id
     * @return
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(@PathVariable(name = "id") Long id) {
        AppConstants.INFOR_LOGGER.info("Controller: delete company");
        return companyService.deleteCompany(id);
    }
}
