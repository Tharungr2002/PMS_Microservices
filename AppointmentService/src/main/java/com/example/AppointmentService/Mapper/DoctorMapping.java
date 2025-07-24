package com.example.AppointmentService.Mapper;

import com.example.AppointmentService.Model.Appointment;
import com.example.AppointmentService.Model.Doctor;
import com.example.AppointmentService.Model.Slot;
import com.example.AppointmentService.Model.Specialization;
import com.example.AppointmentService.Repository.SpecializationRepo;
import com.example.AppointmentService.dto.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DoctorMapping {

    @Autowired
    private SpecializationRepo specializationRepo;

    public static Doctor DoctorDtoToDoctor(String loginId, String name, doctorDto doctordto) {
        UUID uuid = UUID.fromString(loginId);
        Doctor doctor = new Doctor();
        doctor.setPhoneNumber(doctordto.getPhoneNumber());
        doctor.setYearOfExperience(doctordto.getYearOfExperience());
        doctor.setLoginId(uuid);
        doctor.setName(name);

        List<Specialization> specs = doctordto.getSpecializations().stream()
                .map(s -> {
                    Specialization specialization = new Specialization();
                    specialization.setName(s.getName());
                    specialization.setDoctor(doctor);
                    return specialization;
                }).collect(Collectors.toList());

        doctor.setSpecializations(specs);
        return doctor;
    }

    public static doctorDto DoctorToDoctorDto(Doctor doctor) {
        doctorDto doctordto = new doctorDto();
        doctordto.setLoginId(doctor.getLoginId().toString());
        doctordto.setPhoneNumber(doctor.getPhoneNumber());
        doctordto.setYearOfExperience(doctor.getYearOfExperience());

        List<specializationRequestDto> specs = doctor.getSpecializations().stream()
                .map(s->{
                    specializationRequestDto specializationrequestdto =new specializationRequestDto();
                    specializationrequestdto.setName(s.getName());

                    return specializationrequestdto;
                }).collect(Collectors.toList());

        doctordto.setSpecializations(specs);
        return doctordto;
    }

    public static List<DoctorSlotCreation> slotToDoctorSlotCreation(List<Slot> slots) {
        List<DoctorSlotCreation> allSlots = slots.stream()
                .map(s->{
                    DoctorSlotCreation dst = new DoctorSlotCreation();
                    dst.setStartTime(s.getStartTime().toString());
                    dst.setEndTime(s.getEndTime().toString());
                    return dst;
                }).collect(Collectors.toList());
        return allSlots;
    }

    public static List<AvailableSlots> doctorToAvailableSlots(Doctor doctor) {
        List<AvailableSlots> getAllSlots = doctor.getSlots().stream()
                .map(s->{
                    AvailableSlots availableSlots = new AvailableSlots();
                    availableSlots.setStartingTime(s.getStartTime().toString());
                    availableSlots.setEndingTime(s.getEndTime().toString());
                    availableSlots.setSlotId(s.getId().toString());
                    availableSlots.setDoctorId(s.getDoctor().getId().toString());
                    return availableSlots;
                }).collect(Collectors.toList());
        return getAllSlots;
    }

    public static AppointmentResponse returnAppointment(Appointment saved) {
        AppointmentResponse response = new AppointmentResponse();

        response.setAppointmentId(saved.getId().toString());
        response.setDoctorId(saved.getDoctor().getId().toString());
        response.setStatus(saved.getStatus().toString());
        response.setStartTime(saved.getStartingTime().toString());
        response.setEndTime(saved.getEndingTime().toString());
        return response;
    }
}
