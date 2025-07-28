package com.example.patientServices.controller;

import com.example.patientServices.Service.patientService;
import com.example.patientServices.dto.patientRequest.patientRequestDto;
import com.example.patientServices.dto.patinetResponse.patientResponseDto;
import jakarta.validation.Valid;
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

    //get all patients by admin
    @GetMapping
    public ResponseEntity<List<patientResponseDto>> getAllpatients() {
        List<patientResponseDto> patients = patientservice.getAllpatients();
        return ResponseEntity.ok().body(patients);
    }

    //creating patients by admin
    @PostMapping
    public ResponseEntity<patientResponseDto> createPatient(@Valid @RequestBody patientRequestDto patientrequestdto) {
        patientResponseDto createpatientresdto = patientservice.createPatient(patientrequestdto);
        return ResponseEntity.ok().body(createpatientresdto);
    }


    //editing patients by admin
    @PutMapping("/{id}")
    public ResponseEntity<patientResponseDto> updatePatient(@PathVariable UUID id, @RequestBody patientRequestDto patientrequestdto) {
        patientResponseDto patientresponsedto = patientservice.updatePatient(id,patientrequestdto);
        return ResponseEntity.ok().body(patientresponsedto);
    }

    //deleting patient by admin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientservice.deleteByID(id);
        return ResponseEntity.noContent().build();
    }

    //Search with name (For small scale products) with pageable(admin)
    @GetMapping("/search")
    public ResponseEntity<Page<patientResponseDto>> getByName(@RequestParam String name,
                                              @RequestParam int pageno,
                                              @RequestParam int size) {
        Page<patientResponseDto> newPatient = patientservice.getByName(name,pageno,size);
        return ResponseEntity.ok().body(newPatient);
    }


}
