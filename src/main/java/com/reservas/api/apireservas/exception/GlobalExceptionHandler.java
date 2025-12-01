package com.reservas.api.apireservas.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(NotFoundException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleRepeatData(DataIntegrityViolationException ex){
        Map<String, String> response = new HashMap<>();
        String[] valor = ex.getMessage().split("'");

        response.put("error", "Valor '" + valor[1] + "' ya existe");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex){
        Map<String, String> response = new HashMap<>();
        String[] valor = ex.getMessage().split("DayOfWeek.");

        response.put("error", "Valor "+valor[1]+" no existe en dayOfWeek");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }    
}
