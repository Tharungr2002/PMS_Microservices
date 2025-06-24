package com.example.patientServices.Exceptions;

public class patientNotAvailable extends RuntimeException{

    public patientNotAvailable(String message) {
        super(message);
    }
}
