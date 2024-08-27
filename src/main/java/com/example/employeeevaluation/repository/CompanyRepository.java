package com.example.employeeevaluation.repository;

import com.example.employeeevaluation.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByCompanyId(Long companyId);
}
