package com.example.AppointmentService.Service;

import com.example.AppointmentService.Mapper.DoctorMapping;
import com.example.AppointmentService.Model.Doctor;
import com.example.AppointmentService.Model.Specialization;
import com.example.AppointmentService.Repository.SpecializationRepo;
import com.example.AppointmentService.Repository.repo;
import com.example.AppointmentService.dto.DoctorNameResponse;
import com.example.AppointmentService.dto.doctorDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private repo DoctorRepository;

    @Autowired
    private SpecializationRepo specializationRepo;

    public doctorDto storeDoctorDetails(String loginId,String name, doctorDto doctordto) {
        UUID uuid = UUID.fromString(loginId);
        if(DoctorRepository.existsByLoginId(uuid)) {
            throw new RuntimeException("Doctor already exists");
        }
        Doctor doctor= DoctorRepository.save(DoctorMapping.DoctorDtoToDoctor(loginId,name,doctordto));
        System.out.println(specializationRepo.findByDoctorId(doctor.getId()));
        return DoctorMapping.DoctorToDoctorDto(doctor);
    }

    @Transactional
    public void deleteDoctor(String loginId) {
        UUID loginid = UUID.fromString(loginId);

        if(!DoctorRepository.existsByLoginId(loginid)) {
            throw new RuntimeException("Doctor not found Error");
        }
        DoctorRepository.deleteByloginId(loginid);
    }


    public List<DoctorNameResponse> getBySpec(String specialization) {

        List<DoctorNameResponse> specs = specializationRepo.findByname(specialization).stream()
                .map(s->{
                    DoctorNameResponse nameResponse = new DoctorNameResponse();

                    nameResponse.setName(s.getDoctor().getName());
                    return nameResponse;
                }).collect(Collectors.toList());

        return specs;
    }
}