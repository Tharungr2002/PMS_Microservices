package com.example.patientServices.Exceptions;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class globalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> globalValidationHandler(
            MethodArgumentNotValidException ex) {

        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(),error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(emailAlreadyExisting.class)
    public ResponseEntity<Map<String,String>> emailAlreadyExisting(
            emailAlreadyExisting ex
    ) {

        Map<String,String> errors = new HashMap<>();
        errors.put("Error","Email is Alreay existing");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(patientNotAvailable.class)
    public ResponseEntity<Map<String,String>> patientNotAvailableHandler(
            patientNotAvailable ex
    ){
        Map<String,String> errors = new HashMap<>();
        errors.put("Error","Patient is not available");
        return ResponseEntity.badRequest().body(errors);
    }
}
