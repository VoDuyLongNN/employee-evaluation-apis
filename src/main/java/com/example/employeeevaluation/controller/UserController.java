package com.example.employeeevaluation.controller;

import com.example.employeeevaluation.dto.request.ChangePasswordRequest;
import com.example.employeeevaluation.dto.request.UserRequest;
import com.example.employeeevaluation.exceptionhandler.customerException.ApiValidateException;
import com.example.employeeevaluation.service.IUserService;
import com.example.employeeevaluation.util.AppConstants;
import com.example.employeeevaluation.util.ResultObject;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.prefix}/admin/user")
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("{id}/update")
    @PreAuthorize("hasAnyAuthority('ROLE_SYSTEM', 'ROLE_ADMIN')")
    public ResponseEntity<ResultObject> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        AppConstants.INFOR_LOGGER.info("Controller: update user");
        if (bindingResult.hasErrors()) {
            throw new ApiValidateException("Validation Failed: " + bindingResult.getAllErrors());
        }
        ResultObject response = userService.updateUser(id, userRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SYSTEM')")
    public ResponseEntity<ResultObject> deleteUser(@PathVariable Long id) {
        AppConstants.INFOR_LOGGER.info("Controller: delete user");
        ResultObject response = userService.deleteUser(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SYSTEM', 'ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ResultObject> getUserById(@PathVariable Long id) {
        AppConstants.INFOR_LOGGER.info("Controller: get user by id");
        ResultObject response = userService.getUserById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasAnyAuthority('ROLE_SYSTEM', 'ROLE_ADMIN')")
    public ResponseEntity<ResultObject> getAllUsers() {
        AppConstants.INFOR_LOGGER.info("Controller: get all user");
        ResultObject response = userService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/approve/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SYSTEM', 'ROLE_ADMIN')")
    public ResponseEntity<ResultObject> approveUserToAdmin(@PathVariable Long id) {
        AppConstants.INFOR_LOGGER.info("Controller: approve user to admin");
        ResultObject response = userService.approveUserToAdmin(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/change-password/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SYSTEM')")
    public ResponseEntity<ResultObject> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest request, BindingResult bindingResult) {
        AppConstants.INFOR_LOGGER.info("Controller: change password");
        if (bindingResult.hasErrors()) {
            throw new ApiValidateException("Validation Failed: " + bindingResult.getAllErrors());
        }
        ResultObject response = userService.changePassword(id, request.getOldPassword(), request.getNewPassword());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
