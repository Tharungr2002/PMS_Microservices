package com.example.AppointmentService.Controllers;

import com.example.AppointmentService.Enums.AppointmentStatus;
import com.example.AppointmentService.Repository.SpecializationRepo;
import com.example.AppointmentService.Service.DoctorService;
import com.example.AppointmentService.dto.*;
import org.apache.coyote.Response;
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


    //creating doctor by doctor or admin
    @PostMapping("/create")
    public ResponseEntity<doctorDto> createDoctorDetails(@RequestHeader("X-loginId-Headers")String loginId,
                                                         @RequestHeader("X-Name-Headers") String name ,
                                                         @RequestBody doctorDto doctordto) {
        doctorDto doctorDetail = doctorservice.storeDoctorDetails(loginId,name,doctordto);
        return ResponseEntity.ok().body(doctorDetail);
    }

    //deleting doctor by admin or doctor
    @DeleteMapping("/delete/{loginId}")
    public ResponseEntity<Void> deleteDoctorByLoginId(@PathVariable String loginId) {
        doctorservice.deleteDoctor(loginId);
        return ResponseEntity.noContent().build();
    }

    //patient is searching for doctor by specialization
    @GetMapping("/getDoctorBySpec/{specialization}")
    public ResponseEntity<List<DoctorNameResponse>> getDoctorBySpecialization(@PathVariable String specialization) {
        List<DoctorNameResponse> allSpec = doctorservice.getBySpec(specialization);
        return ResponseEntity.ok(allSpec);
    }

    //Doctor is creating his slots
    @PostMapping("/createSlot/{loginId}")
    public ResponseEntity<List<DoctorSlotCreation>> createSlot(@PathVariable String loginId,
                                                               @RequestBody DoctorSlotCreation doctorSlotCreation) {

        List<DoctorSlotCreation> allSlots = doctorservice.createSlots(loginId,doctorSlotCreation);
        return ResponseEntity.ok(allSlots);
    }

    //If patient select doctor, should return available slots.
    @GetMapping("/getAvailSlot/{DoctorName}")
    public ResponseEntity<List<AvailableSlots>> returnAvailableSlots(@PathVariable String DoctorName) {

        List<AvailableSlots> allSlots = doctorservice.returnAllSlots(DoctorName);
        return ResponseEntity.ok(allSlots);

    }

    @PostMapping("/appointment")
    public ResponseEntity<AppointmentResponse> bookAppointment(@RequestBody AvailableSlots availableSlots,
                                                               @RequestHeader("X-PatientId") String patientId) {
        AppointmentResponse appointmentResponse = doctorservice.bookAppointment(availableSlots,patientId);
        return ResponseEntity.ok(appointmentResponse);
    }

    @GetMapping("/get/appointment/booked/{status}")
    public ResponseEntity<List<AppointmentBooked>> getAllAppointment(@RequestHeader("X-PatientId") String patientId ,@PathVariable String status) {

        List<AppointmentBooked> appointmentBooked = doctorservice.GetAllBookedAppointmentByPatient(patientId , status);
        return ResponseEntity.ok(appointmentBooked);
    }


//    @PostMapping("/cancel/appointment")
//    public ResponseEntity<CancelAppointmentResponse> cancelAppointment(@RequestHeader("X-PatientId") String patientId) {
//
//        CancelAppointmentResponse response = doctorservice.CancelAppointment(patientId);
//        return ResponseEntity.ok(response);
//    }






}
