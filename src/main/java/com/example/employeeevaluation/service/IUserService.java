package com.example.employeeevaluation.service;

import com.example.employeeevaluation.dto.request.SignInRequest;
import com.example.employeeevaluation.dto.request.SignOutRequest;
import com.example.employeeevaluation.dto.request.UserRequest;
import com.example.employeeevaluation.util.ResultObject;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("unused")
public interface IUserService {
    Boolean checkUsernameAvailability(String username);

    Boolean checkEmailAvailability(String email);

    Boolean validateEmail(String email);

    ResultObject registerUser(UserRequest userRequest);

    ResponseEntity<ResultObject> signIn(SignInRequest request);

    ResponseEntity<ResultObject> signOut(SignOutRequest request);

    ResultObject updateUser(Long userId, UserRequest userRequest);

    ResultObject deleteUser(Long userId);

    ResultObject getUserById(Long userId);

    ResultObject getAllUsers();

    ResultObject approveUserToAdmin(Long userId);

    ResultObject changePassword(Long userId, String oldPassword, String newPassword);
}
