package com.example.billing.billingService.Controller;

import com.example.billing.billingService.Service.PrescriptionService;
import com.example.billing.billingService.dto.PrescriptionRequest;
import com.example.billing.billingService.dto.PrescriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;


    @PostMapping("doctor/create")
    public ResponseEntity<PrescriptionResponse> createPrescription(@RequestBody List<PrescriptionRequest> prescriptionRequest ,
                                                                         @RequestHeader("X-PatientId") String patientid ,
                                                                         @RequestHeader("X-DoctorId") String doctorId ,
                                                                   @RequestHeader("X-AppointmentId") String appointmentId) {
            PrescriptionResponse response = prescriptionService.createPrescription(patientid,doctorId,prescriptionRequest,appointmentId);
            return ResponseEntity.ok(response);
    }


}
