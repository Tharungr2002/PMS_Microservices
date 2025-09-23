package com.example.patientServices.dto.patinetResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientContacts {

    private String email;

    private String phoneNumber;

    private UUID patientId;

    private String message;


}
