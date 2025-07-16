package com.example.AppointmentService.Service;

import com.example.AppointmentService.Mapper.DoctorMapping;
import com.example.AppointmentService.Model.Doctor;
import com.example.AppointmentService.Repository.repo;
import com.example.AppointmentService.dto.doctorDto.doctorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DoctorService {

    @Autowired
    private repo DoctorRepository;

    public doctorDto storeDoctorDetails(String loginId, doctorDto doctordto) {
        UUID uuid = UUID.fromString(loginId);
        if(DoctorRepository.existsByLoginId(uuid)) {
            throw new RuntimeException("Doctor already exists");
        }
        Doctor doctor= DoctorRepository.save(DoctorMapping.DoctorDtoToDoctor(loginId,doctordto));
        return DoctorMapping.DoctorToDoctorDto(doctor);
    }
}
