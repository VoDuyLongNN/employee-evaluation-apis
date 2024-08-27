package com.example.employeeevaluation.controller;

import com.example.employeeevaluation.config.LocalizationUtils;
import com.example.employeeevaluation.util.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/test-language")
public class TestLanguageController {
    private final LocalizationUtils localizationUtils;
    @GetMapping
    public ResponseEntity<?> test() {

           String message = localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY);
            return ResponseEntity.ok().body(message);
    }
}
