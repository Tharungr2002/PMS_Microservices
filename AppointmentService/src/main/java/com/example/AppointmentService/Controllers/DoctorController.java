package com.example.AppointmentService.Controllers;

import com.example.AppointmentService.Service.DoctorService;
import com.example.AppointmentService.dto.doctorDto.doctorDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorservice;


    @PostMapping
    public ResponseEntity<doctorDto> createDoctorDetails(@RequestHeader("X-loginId-Headers")String loginId,@Valid @RequestBody doctorDto doctordto) {
        doctorDto doctorDetail = doctorservice.storeDoctorDetails(loginId,doctordto);
        return ResponseEntity.ok().body(doctorDetail);
    }
}
