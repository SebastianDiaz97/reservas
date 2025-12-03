package com.reservas.api.apireservas.exception;

public class ValidationException extends RuntimeException{
    public ValidationException(String msj){
        super(msj);
    }
}
