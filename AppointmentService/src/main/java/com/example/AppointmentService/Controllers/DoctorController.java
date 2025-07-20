package com.example.AppointmentService.Controllers;

import com.example.AppointmentService.Repository.SpecializationRepo;
import com.example.AppointmentService.Service.DoctorService;
import com.example.AppointmentService.dto.DoctorNameResponse;
import com.example.AppointmentService.dto.doctorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorservice;

    @Autowired
    private SpecializationRepo repocheck;


    @PostMapping("/create")
    public ResponseEntity<doctorDto> createDoctorDetails(@RequestHeader("X-loginId-Headers")String loginId,
                                                         @RequestHeader("X-Name-Headers") String name ,
                                                         @RequestBody doctorDto doctordto) {
        doctorDto doctorDetail = doctorservice.storeDoctorDetails(loginId,name,doctordto);
        return ResponseEntity.ok().body(doctorDetail);
    }

    @DeleteMapping("/delete/{loginId}")
    public ResponseEntity<Void> deleteDoctorByLoginId(@PathVariable String loginId) {
        doctorservice.deleteDoctor(loginId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getDoctorBySpec/{specialization}")
    public ResponseEntity<List<DoctorNameResponse>> getDoctorBySpecialization(@PathVariable String specialization) {
        List<DoctorNameResponse> allSpec = doctorservice.getBySpec(specialization);
        return ResponseEntity.ok(allSpec);
    }




}
