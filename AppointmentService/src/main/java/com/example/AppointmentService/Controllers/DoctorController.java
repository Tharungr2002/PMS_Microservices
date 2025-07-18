package com.example.AppointmentService.Controllers;

import com.example.AppointmentService.Service.DoctorService;
import com.example.AppointmentService.dto.doctorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorservice;


    @PostMapping("/create")
    public ResponseEntity<doctorDto> createDoctorDetails(@RequestHeader("X-loginId-Headers")String loginId,@RequestBody doctorDto doctordto) {
        doctorDto doctorDetail = doctorservice.storeDoctorDetails(loginId,doctordto);
        return ResponseEntity.ok().body(doctorDetail);
    }

    @DeleteMapping("/delete/{loginId}")
    public ResponseEntity<Void> deleteDoctorByLoginId(@PathVariable String loginId) {
        doctorservice.deleteDoctor(loginId);
        return ResponseEntity.noContent().build();
    }
}
