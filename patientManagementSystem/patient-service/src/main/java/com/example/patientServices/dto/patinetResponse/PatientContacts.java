package com.example.patientServices.dto.patinetResponse;

import com.example.patientServices.Model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientContacts {

    private String email;

    private String phoneNumber;

    private UUID patientId;

    private String message;

    public PatientContacts(String email , String phoneNumber , UUID patientId) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.patientId = patientId;
    }


}
