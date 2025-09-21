package com.example.patientServices.Service;

import com.example.patientServices.Exceptions.emailAlreadyExisting;
import com.example.patientServices.Exceptions.patientNotAvailable;
import com.example.patientServices.Model.Patient;
import com.example.patientServices.Repository.patientRepository;
import com.example.patientServices.dto.patientRequest.PatientReqByPatient;
import com.example.patientServices.dto.patientRequest.patientRequestDto;
import com.example.patientServices.dto.patinetResponse.patientResponseDto;
import com.example.patientServices.grpc.billingGrpcClient;
import com.example.patientServices.kakfa.kakfaproducer;
import com.example.patientServices.mapper.patientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class patientService {
    @Autowired
    private kakfaproducer Kakfaproducer;

    @Autowired
    private billingGrpcClient billinggrcpclient;

    @Autowired
    private patientRepository patientrepository;

    public List<patientResponseDto> getAllpatients() {
        List<Patient> patients = patientrepository.findAll();

        return patients.stream().map(patientMapper::patientMapping).toList();

    }

    public patientResponseDto createPatient(patientRequestDto patientrequestdto) {
        if(patientrepository.existsByEmail(patientrequestdto.getEmail())) {
            throw new emailAlreadyExisting("Email id already exist : " +
                    patientrequestdto.getEmail());
        }

        System.out.println("---------------------------------------------------------------");

        Patient newPatient = patientrepository.save(patientMapper.createPatientMap(patientrequestdto));
        System.out.println(newPatient.getId());

        //grpc request
//        System.out.println(billinggrcpclient.createBillingAccount(newPatient.getId().toString(), newPatient.getName()));
//
//        //kakfa to analytical services
//        Kakfaproducer.sendingToAnalyticalService(newPatient);

        return patientMapper.patientMapping(newPatient);
    }

    public patientResponseDto updatePatient(UUID id, patientRequestDto patientrequestdto) {
        Patient patient = patientrepository.findById(id)
                .orElseThrow(() -> new patientNotAvailable("Id did not found!!"));
        patient.setName(patientrequestdto.getName());
        patient.setAddress(patientrequestdto.getAddress());
        patient.setEmail(patientrequestdto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientrequestdto.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientrequestdto.getRegisteredDate()));

        Patient updatedPatient = patientrepository.save(patient);
        return patientMapper.patientMapping(updatedPatient);
    }

    public void deleteByID(UUID id) {
        patientrepository.deleteById(id);
    }

    public Page<patientResponseDto> getByName(String name, int pageno, int size) {
        Pageable pageable = PageRequest.of(pageno,size);
        Page<Patient> patients = patientrepository.findByNameContainingIgnoreCase(name,pageable);
        return patients.map(patientMapper::patientMapping);
    }

    public patientResponseDto createPatientByPatient(PatientReqByPatient patientReqByPatient, String email, String loginId,String name) {
        if(patientrepository.existsByEmail(email)) {
           throw new RuntimeException("User Already exists please use another Email Id");
        }

        Patient patient = patientrepository.save(patientMapper.PatientReqToPatient(patientReqByPatient,email,loginId,name));

        return patientMapper.PatientCreateByPatient(patientReqByPatient,email,loginId,name);

    }
}
