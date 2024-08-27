package com.example.employeeevaluation.service.impl;

import com.example.employeeevaluation.config.LocalizationUtils;
import com.example.employeeevaluation.dto.request.SignInRequest;
import com.example.employeeevaluation.dto.request.SignOutRequest;
import com.example.employeeevaluation.dto.request.UserRequest;
import com.example.employeeevaluation.dto.response.SignInResponse;
import com.example.employeeevaluation.entity.Company;
import com.example.employeeevaluation.entity.Role;
import com.example.employeeevaluation.entity.Token;
import com.example.employeeevaluation.entity.User;
import com.example.employeeevaluation.entity.enums.ERole;
import com.example.employeeevaluation.exceptionhandler.customerException.ApiValidateException;
import com.example.employeeevaluation.repository.CompanyRepository;
import com.example.employeeevaluation.repository.RoleRepository;
import com.example.employeeevaluation.repository.TokenRepository;
import com.example.employeeevaluation.repository.UserRepository;
import com.example.employeeevaluation.service.IUserService;
import com.example.employeeevaluation.util.AppConstants;
import com.example.employeeevaluation.util.InputValidator;
import com.example.employeeevaluation.util.MessageKeys;
import com.example.employeeevaluation.util.ResultObject;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final JwtServiceImpl jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final LocalizationUtils localizationUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, CompanyRepository companyRepository, AuthenticationManager authenticationManager, JwtServiceImpl jwtService, TokenRepository tokenRepository, LocalizationUtils localizationUtils, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.localizationUtils = localizationUtils;
    }

    @Override
    public Boolean checkUsernameAvailability(String username) {
        return !userRepository.existsByUserName(username);
    }

    @Override
    public Boolean checkEmailAvailability(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public Boolean validateEmail(String email) {
        return InputValidator.patternMatches(email, InputValidator.emailRegexPattern);
    }

    @Override
    public ResultObject registerUser(UserRequest userRequest) {
        try {
            if (!checkUsernameAvailability(userRequest.getUserName())) {
                throw new ApiValidateException("Username has been existed");
            }
            if (!checkEmailAvailability(userRequest.getEmail())) {
                throw new ApiValidateException("Email has been existed");
            }
            if (!validateEmail(userRequest.getEmail())) {
                throw new ApiValidateException("Email is not valid");
            }

            User user = new User();
            user.setUserName(userRequest.getUserName());
            user.setFullName(userRequest.getFullName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

            Set<Role> roles = new HashSet<>();

            if (userRepository.count() == 0) {
                Role systemRole = new Role();
                systemRole.setRoleName(ERole.ROLE_SYSTEM);
                roles.add(systemRole);

                Role adminRole = new Role();
                adminRole.setRoleName(ERole.ROLE_ADMIN);
                roles.add(adminRole);

                Role userRole = new Role();
                userRole.setRoleName(ERole.ROLE_USER);
                roles.add(userRole);

            } else {
                Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new Exception("User Role not set."));
                roles.add(userRole);
            }

            // Save roles to the database
            roleRepository.saveAll(roles);
            user.setRole(roles);
            Company company = companyRepository.findById(userRequest.getCompanyId()).orElse(null);
            if (company == null) {
                throw new ApiValidateException("CompanyResponse not found");
            }
            user.setCompany(company);

            User savedUser = userRepository.save(user);

            return ResultObject.builder().status("SUCCESS").data(savedUser).messages(Collections.singletonList("User created successfully")).build();
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error occurred during user registration: " + e.getMessage());
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            throw new ApiValidateException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ResultObject> signIn(SignInRequest request) {
        ResultObject resultObject;
        SignInResponse signInResponse;
        Token token;
        HttpStatus httpStatus;

        try {
            var user = userRepository.findByUserName(request.getUserName()).orElseThrow(() -> new ApiValidateException(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAILED)));
            var jwtToken = jwtService.generateToken(user);
            var jwtRefreshToken = jwtService.generateRefreshToken(user);

            Date expire = jwtService.getExpirationTimeFromToken(jwtToken, jwtService.getSecretKey());

            //save token to database
            token = Token.builder()
                    .user(user)
                    .tokenString(jwtToken)
                    .expire(expire)
                    .build();

            tokenRepository.save(token);
            signInResponse = SignInResponse.builder()
                    .userId(user.getUserId())
                    .role(user.getRole())
                    .token(jwtToken)
                    .refreshToken(jwtRefreshToken)
                    .expirationTime(expire)
                    .build();

            resultObject = ResultObject.builder()
                    .status(HttpStatus.OK.name())
                    .messages(Collections.singletonList(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY)))
                    .data(signInResponse)
                    .build();

            httpStatus = HttpStatus.OK;

        } catch (ApiValidateException e){
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            resultObject = ResultObject.builder()
                .status(HttpStatus.UNAUTHORIZED.name())
                .messages(Collections.singletonList(e.getMessage()))
                .build();

            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            resultObject = ResultObject.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .messages(Collections.singletonList(e.getMessage()))
                    .build();

            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultObject, httpStatus);
    }

    @Override
    public ResponseEntity<ResultObject> signOut(SignOutRequest request) {
        ResultObject signOutObject;
        HttpStatus httpStatus;
        Token token;
        try {
            token = tokenRepository.findByTokenString(request.getToken()).orElseThrow(() -> new ApiValidateException("Logout failed, Token is not found!"));
            tokenRepository.delete(token);

            signOutObject = ResultObject.builder()
                    .status(HttpStatus.OK.name())
                    .messages(Collections.singletonList("Logout Successful"))
                    .build();
            httpStatus = HttpStatus.OK;

        } catch (ApiValidateException e){
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            signOutObject = ResultObject.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .messages(Collections.singletonList(e.getMessage()))
                .build();
            httpStatus = HttpStatus.NOT_FOUND;

        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            signOutObject = ResultObject.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .messages(Collections.singletonList(e.getMessage()))
                    .build();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(signOutObject, httpStatus);
    }

    @Override
    public ResultObject updateUser(Long userId, UserRequest userRequest) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new ApiValidateException("User not found"));
            if (userRequest.getUserName() != null) {
                if (!checkUsernameAvailability(userRequest.getUserName())) {
                    throw new ApiValidateException("Username has been existed");
                }
                user.setUserName(userRequest.getUserName());
            }
            if (userRequest.getFullName() != null) {
                user.setFullName(userRequest.getFullName());
            }
            if (userRequest.getEmail() != null) {
                if (!checkEmailAvailability(userRequest.getEmail())) {
                    throw new ApiValidateException("Email has been existed");
                }
                if (!validateEmail(userRequest.getEmail())) {
                    throw new ApiValidateException("Email is not valid");
                }
                user.setEmail(userRequest.getEmail());
            }
            userRepository.save(user);
            return ResultObject.builder().status("SUCCESS").data(user).messages(Collections.singletonList("User updated successfully")).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            // Log the exception for debugging
            System.err.println("Error occurred during user update: " + e.getMessage());
            throw new ApiValidateException(e.getMessage());
        }
    }

    @Override
    public ResultObject deleteUser(Long userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new ApiValidateException("User not found"));
            userRepository.delete(user);
            return ResultObject.builder().status("SUCCESS").data(user).messages(Collections.singletonList("User deleted successfully")).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            // Log the exception for debugging
            System.err.println("Error occurred during user deletion: " + e.getMessage());
            throw new ApiValidateException(e.getMessage());
        }
    }

    @Override
    public ResultObject getUserById(Long userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new ApiValidateException("User not found"));
            return ResultObject.builder().status("SUCCESS").data(user).messages(Collections.singletonList("User retrieved successfully")).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            // Log the exception for debugging
            System.err.println("Error occurred during user retrieval: " + e.getMessage());
            throw new ApiValidateException(e.getMessage());
        }
    }

    @Override
    public ResultObject getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return ResultObject.builder().status("SUCCESS").data(users).messages(Collections.singletonList("Users retrieved successfully")).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            // Log the exception for debugging
            System.err.println("Error occurred during users retrieval: " + e.getMessage());
            throw new ApiValidateException(e.getMessage());
        }
    }

    @Override
    public ResultObject approveUserToAdmin(Long userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new ApiValidateException("User not found"));
            if (user.getRole().stream().anyMatch(r -> r.getRoleName().equals(ERole.ROLE_ADMIN))) {
                throw new ApiValidateException("User is already an admin");
            }
            Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN).orElseThrow(() -> new ApiValidateException("Admin Role not set."));
            user.setRole(Set.of(adminRole));
            userRepository.save(user);
            return ResultObject.builder().status("SUCCESS").data(user).messages(Collections.singletonList("User approved to admin successfully")).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            // Log the exception for debugging
            System.err.println("Error occurred during user approval to admin: " + e.getMessage());
            throw new ApiValidateException(e.getMessage());
        }
    }

    @Override
    public ResultObject changePassword(Long userId, String oldPassword, String newPassword) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new ApiValidateException("User not found"));
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new ApiValidateException("Old password is incorrect");
            }
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return ResultObject.builder().status("SUCCESS").data(user).messages(Collections.singletonList("Password changed successfully")).build();
        } catch (Exception e) {
            AppConstants.ERROR_LOGGER.error(e.getMessage());
            // Log the exception for debugging
            System.err.println("Error occurred during password change: " + e.getMessage());
            throw new ApiValidateException(e.getMessage());
        }
    }

}
