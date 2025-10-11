package com.example.AppointmentService.Controllers;

import com.example.AppointmentService.Model.specializationDb;
import com.example.AppointmentService.Service.specializationService;
import com.example.AppointmentService.dto.DoctorNameResponse;
import com.example.AppointmentService.dto.specializationDbResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@RestController
@RequestMapping("/specialization")
public class specializationController {

    @Autowired
    private specializationService specializationservice;

    //add doctor specialization by admin
    @PostMapping("admin/add")
    public ResponseEntity<specializationDb> addSpecializtion(@RequestBody specializationDb specializationdb) {
        specializationDb newSpec = specializationservice.addSpecialization(specializationdb);
        return ResponseEntity.ok(newSpec);
    }

    //get all specialization by admin
    @GetMapping("admin/getAll")
    public ResponseEntity<List<specializationDbResponse>> getAll() {
        List<specializationDbResponse> allSpec = specializationservice.getAll();
        return ResponseEntity.ok(allSpec);
    }


}
