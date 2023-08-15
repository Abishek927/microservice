package com.abi.user.service.exceptions;

import com.abi.user.service.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceHandler(ResourceNotFoundException exception){
    String message=exception.getMessage();
     ApiResponse apiResponse=ApiResponse.builder().message(message).success(true).httpStatus(HttpStatus.NOT_FOUND).build();
    return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);

    }

}
