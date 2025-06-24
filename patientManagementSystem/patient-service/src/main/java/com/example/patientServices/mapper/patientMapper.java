package com.example.patientServices.mapper;

import com.example.patientServices.Model.Patient;
import com.example.patientServices.dto.patientRequestDto;
import com.example.patientServices.dto.patientResponseDto;

import java.time.LocalDate;

public class patientMapper {

    public static patientResponseDto patientMapping(Patient patient) {
        patientResponseDto patientresponsedto = new patientResponseDto();
        patientresponsedto.setId(patient.getId().toString());
        patientresponsedto.setName(patient.getName());
        patientresponsedto.setEmail(patient.getEmail());
        patientresponsedto.setAddress(patient.getAddress());
        patientresponsedto.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientresponsedto;
    }

    public static Patient createPatientMap(patientRequestDto patientrequestdto) {
        Patient patient = new Patient();
        patient.setAddress(patientrequestdto.getAddress());
        patient.setName(patientrequestdto.getName());
        patient.setEmail(patientrequestdto.getEmail());
        patient.setRegisteredDate(LocalDate.parse(patientrequestdto.getRegisteredDate()));
        patient.setDateOfBirth(LocalDate.parse(patientrequestdto.getDateOfBirth()));
        return patient;
    }
}
