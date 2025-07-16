package com.example.AppointmentService.Mapper;

import com.example.AppointmentService.Model.Doctor;
import com.example.AppointmentService.dto.doctorDto.doctorDto;

import java.util.UUID;

public class DoctorMapping {

    public static Doctor DoctorDtoToDoctor(String loginId,doctorDto doctordto) {
        UUID uuid =  UUID.fromString(loginId);
        Doctor doctor = new Doctor();
        doctor.setLoginId(uuid);
        doctor.setPhoneNumber(doctordto.getPhoneNumber());
        doctor.setYearOfExperience(doctordto.getYearOfExperience());
        doctor.setSpecialization(doctordto.getSpecialization());
        return doctor;
    }

    public static doctorDto DoctorToDoctorDto(Doctor doctor) {
        doctorDto doctordto = new doctorDto();
        doctordto.setLoginId(doctor.getLoginId().toString());
        doctordto.setSpecialization(doctor.getSpecialization());
        doctordto.setPhoneNumber(doctor.getPhoneNumber());
        doctordto.setYearOfExperience(doctor.getYearOfExperience());
        return doctordto;
    }
}
