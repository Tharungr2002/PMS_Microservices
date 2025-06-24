package com.example.patientServices.Service;

import com.example.patientServices.Exceptions.emailAlreadyExisting;
import com.example.patientServices.Exceptions.patientNotAvailable;
import com.example.patientServices.Model.Patient;
import com.example.patientServices.Repository.patientRepository;
import com.example.patientServices.RestConfig.restConfig;
import com.example.patientServices.dto.billing.billingDto;
import com.example.patientServices.dto.createPatientResDto;
import com.example.patientServices.dto.patientRequestDto;
import com.example.patientServices.dto.patientResponseDto;
import com.example.patientServices.mapper.patientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class patientService {

    @Autowired
    private restConfig restconfig;

    @Autowired
    private patientRepository patientrepository;

    public List<patientResponseDto> getAllpatients() {
        List<Patient> patients = patientrepository.findAll();

        return patients.stream().map(patientMapper::patientMapping).toList();

    }

    public createPatientResDto createPatient(patientRequestDto patientrequestdto) {
        if(patientrepository.existsByEmail(patientrequestdto.getEmail())) {
            throw new emailAlreadyExisting("Email id already exist : " +
                    patientrequestdto.getEmail());
        }
        Patient newPatient = patientrepository.save(patientMapper.createPatientMap(patientrequestdto));

        String patientId = newPatient.getId().toString();
        String url = "http://localhost:8081/billing/"+patientId;

        billingDto billingdtoresponse = restconfig.apiCall().getForObject(url, billingDto.class);
        patientResponseDto patientresponse = patientMapper.patientMapping(newPatient);

        createPatientResDto createpatientresponse = new createPatientResDto();
        createpatientresponse.setBillingdto(billingdtoresponse);
        createpatientresponse.setPatientresponsedto(patientresponse);
        return createpatientresponse;

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
}
