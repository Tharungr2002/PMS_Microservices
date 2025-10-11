package com.example.patientServices.controller;

import com.example.patientServices.Service.patientService;
import com.example.patientServices.dto.patientRequest.CronAptPatientNotification;
import com.example.patientServices.dto.patientRequest.PatientReqByPatient;
import com.example.patientServices.dto.patientRequest.patientRequestDto;
import com.example.patientServices.dto.patinetResponse.PatientContact;
import com.example.patientServices.dto.patinetResponse.PatientContacts;
import com.example.patientServices.dto.patinetResponse.patientResponseDto;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/patients")
@RestController
public class patientController {

    @Autowired
    private patientService patientservice;

    //create patient by patient
    @PostMapping("/createPatient/bypatient")
    public ResponseEntity<patientResponseDto> createPatientBypatient(@RequestBody PatientReqByPatient patientReqByPatient,
                                                                     @RequestHeader("X-email") String email,
                                                                     @RequestHeader("X-loginId") String loginId,
                                                                     @RequestHeader("X-name") String name) {
        patientResponseDto patient = patientservice.createPatientByPatient(patientReqByPatient,email,loginId,name);
        return ResponseEntity.ok(patient);
    }

    //get all patients by admin
    @GetMapping("/admin/getall")
    public ResponseEntity<List<patientResponseDto>> getAllpatients() {
        List<patientResponseDto> patients = patientservice.getAllpatients();
        return ResponseEntity.ok().body(patients);
    }

    //creating patients by admin
    @PostMapping("/admin/create")
    public ResponseEntity<patientResponseDto> createPatient(@Valid @RequestBody patientRequestDto patientrequestdto) {
        patientResponseDto createpatientresdto = patientservice.createPatient(patientrequestdto);
        return ResponseEntity.ok().body(createpatientresdto);
    }


    //editing patients by admin
    @PutMapping("/admin/{id}")
    public ResponseEntity<patientResponseDto> updatePatient(@PathVariable UUID id, @RequestBody patientRequestDto patientrequestdto) {
        patientResponseDto patientresponsedto = patientservice.updatePatient(id,patientrequestdto);
        return ResponseEntity.ok().body(patientresponsedto);
    }

    //deleting patient by admin
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientservice.deleteByID(id);
        return ResponseEntity.noContent().build();
    }

    //Search with name (For small scale products) with pageable(admin)
    @GetMapping("/admin/search")
    public ResponseEntity<Page<patientResponseDto>> getByName(@RequestParam String name,
                                              @RequestParam int pageno,
                                              @RequestParam int size) {
        Page<patientResponseDto> newPatient = patientservice.getByName(name,pageno,size);
        return ResponseEntity.ok().body(newPatient);
    }

    //get all patients phonenumber and mail by patientId for cron job
    @PostMapping("/admin/getAll/contacts")
    public ResponseEntity<List<PatientContacts>> getAllPatientsContacts(@RequestBody List<CronAptPatientNotification> cronAptPatientNotification) {
        List<PatientContacts> contacts = patientservice.getAllPatientContacts(cronAptPatientNotification);
        return ResponseEntity.ok(contacts);
    }

    //get email and phonenumber by patientid internal api
    @GetMapping("/admin/getcontact")
    public ResponseEntity<PatientContact> getBypatientid(@RequestHeader("X-Patient-id") String patientid) {
        PatientContact response = patientservice.getpatientdetails(patientid);
        return ResponseEntity.ok(response);
    }


}
