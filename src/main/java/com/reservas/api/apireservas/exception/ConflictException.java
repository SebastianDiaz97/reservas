package com.reservas.api.apireservas.exception;

public class ConflictException extends RuntimeException{
    public ConflictException(String msj){
        super(msj);
    }
}
