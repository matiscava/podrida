package com.example.podrida.exception;

import com.example.podrida.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionConfig {
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<?> idNotFoundException(IdNotFoundException ex){
        ErrorDto errorDto = new ErrorDto(404,ex.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<?> invalidValueException(InvalidValueException ex){
        ErrorDto errorDto = new ErrorDto(404,ex.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}
