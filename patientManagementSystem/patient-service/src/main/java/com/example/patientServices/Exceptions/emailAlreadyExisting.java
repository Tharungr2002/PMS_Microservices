package com.example.patientServices.Exceptions;

public class emailAlreadyExisting extends RuntimeException{

    public emailAlreadyExisting(String message) {
        super(message);
    }
}
