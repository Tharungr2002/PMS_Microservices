package com.example.billing.billingService.Service;

import com.example.billing.billingService.Enum.PrescriptionStatus;
import com.example.billing.billingService.Model.Prescription;
import com.example.billing.billingService.Model.PrescriptionItem;
import com.example.billing.billingService.Repository.PrescriptionItemRepository;
import com.example.billing.billingService.Repository.PrescriptionRepository;
import com.example.billing.billingService.dto.PrescriptionRequest;
import com.example.billing.billingService.dto.PrescriptionResponse;
import com.example.billing.billingService.mapper.PrescriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionItemRepository prescriptionItemRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public PrescriptionResponse createPrescription(String patientid, String doctorId, List<PrescriptionRequest> prescriptionRequest, String appointmentId) {

        try {
            if(prescriptionRepository.existsByAppointmentId(appointmentId)) {
                throw new RuntimeException("Prescription Already Found!");
            }

            List<PrescriptionItem> prescriptionItems = PrescriptionMapper.presReqToPresItems(prescriptionRequest);
            prescriptionItemRepository.saveAll(prescriptionItems);

            Prescription prescription = new Prescription();
            prescription.setItems(prescriptionItems);
            prescription.setDoctorId(doctorId);
            prescription.setPatientId(patientid);
            prescription.setCreatedAt(LocalDateTime.now().toString());
            prescription.setAppointmentId(appointmentId);
            prescriptionRepository.save(prescription);

            PrescriptionResponse response = new PrescriptionResponse();
            response.setStatus(PrescriptionStatus.SUCCESS);
            response.setPrescription(prescription);
            return response;
        }catch(Exception e){
            throw new RuntimeException("Failed to create Prescripton" , e);
        }
    }
}
