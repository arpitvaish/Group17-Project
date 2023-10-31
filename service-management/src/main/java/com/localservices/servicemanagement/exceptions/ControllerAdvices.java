package com.localservices.servicemanagement.exceptions;

import com.localservices.servicemanagement.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException notFoundExcpetion) {
        return new ResponseEntity(
                new ExceptionDto(HttpStatus.NOT_FOUND, notFoundExcpetion.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UnableToCreateServiceException.class)
    public ResponseEntity<ExceptionDto> handleUnableToCreateServiceException(UnableToCreateServiceException unableToCreateServiceException) {
        return new ResponseEntity(
                new ExceptionDto(HttpStatus.BAD_REQUEST, unableToCreateServiceException.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

}
