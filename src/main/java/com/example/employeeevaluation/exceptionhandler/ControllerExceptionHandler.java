package com.example.employeeevaluation.exceptionhandler;

import com.example.employeeevaluation.dto.response.ApiResponse;
import com.example.employeeevaluation.exceptionhandler.customerException.ApiValidateException;
import com.example.employeeevaluation.util.ErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiValidateException.class)
    public ResponseEntity<ErrorObject> resourceNotFoundException(ApiValidateException ex, WebRequest request){
        ErrorObject message = ErrorObject
                .builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<ErrorObject>(message, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> resolveException(ResourceNotFoundException exception) {
        ApiResponse apiResponse = exception.getApiResponse();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handlerException(Exception ex, WebRequest request){
        ErrorObject message = ErrorObject
                .builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<ErrorObject>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
