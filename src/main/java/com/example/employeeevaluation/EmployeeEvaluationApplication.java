package com.example.employeeevaluation;

import com.example.employeeevaluation.util.AppConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmployeeEvaluationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeEvaluationApplication.class, args);
        AppConstants.INFOR_LOGGER.info("Start program: EmployeeEvaluationApplication");
    }

}
