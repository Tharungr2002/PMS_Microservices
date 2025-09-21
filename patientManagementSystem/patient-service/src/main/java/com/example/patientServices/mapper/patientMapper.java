package com.example.patientServices.mapper;

import com.example.patientServices.Model.Patient;
import com.example.patientServices.dto.patientRequest.PatientReqByPatient;
import com.example.patientServices.dto.patientRequest.patientRequestDto;
import com.example.patientServices.dto.patinetResponse.patientResponseDto;

import java.time.LocalDate;
import java.util.UUID;

public class patientMapper {

    public static patientResponseDto patientMapping(Patient patient) {
        patientResponseDto patientresponsedto = new patientResponseDto();
        patientresponsedto.setPhoneNumber(patient.getPhoneNumber());
        patientresponsedto.setId(patient.getId().toString());
        patientresponsedto.setName(patient.getName());
        patientresponsedto.setEmail(patient.getEmail());
        patientresponsedto.setAddress(patient.getAddress());
        patientresponsedto.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientresponsedto;
    }

    public static Patient createPatientMap(patientRequestDto patientrequestdto) {
        Patient patient = new Patient();
        patient.setPhoneNumber(patientrequestdto.getPhoneNumber());
        patient.setGender(patientrequestdto.getGender());
        patient.setAddress(patientrequestdto.getAddress());
        patient.setName(patientrequestdto.getName());
        patient.setEmail(patientrequestdto.getEmail());
        patient.setRegisteredDate(LocalDate.parse(patientrequestdto.getRegisteredDate()));
        patient.setDateOfBirth(LocalDate.parse(patientrequestdto.getDateOfBirth()));
        return patient;
    }

    public static patientResponseDto PatientCreateByPatient(PatientReqByPatient patientReqByPatient, String email, String loginId,String name) {
        patientResponseDto patient = new patientResponseDto();
        patient.setAddress(patientReqByPatient.getAddress());
        patient.setName(name);
        patient.setEmail(email);
        patient.setDateOfBirth(patientReqByPatient.getDateOfBirth());
        return patient;
    }

    public static Patient PatientReqToPatient(PatientReqByPatient patientReqByPatient, String email, String loginId, String name) {

        Patient patient = new Patient();
        patient.setRegisteredDate(LocalDate.parse(patientReqByPatient.getRegisteredDate()));
        patient.setName(name);
        patient.setGender(patientReqByPatient.getGender());
        patient.setEmail(email);
        patient.setAddress(patientReqByPatient.getAddress());
        patient.setLoginId(UUID.fromString(loginId));
        patient.setDateOfBirth(LocalDate.parse(patientReqByPatient.getDateOfBirth()));
        return patient;
    }
}
