package com.example.patientServices.dto.patientRequest;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientReqByPatient {
    private String gender;

    private String address;

    private String dateOfBirth;

    private String registeredDate;

}


