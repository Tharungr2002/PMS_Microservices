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

    public PrescriptionResponse createPrescription(String patientid, String doctorId, List<PrescriptionRequest> prescriptionRequest) {

        try {

            List<PrescriptionItem> prescriptionItems = PrescriptionMapper.presReqToPresItems(prescriptionRequest);
            prescriptionItemRepository.saveAll(prescriptionItems);

//            if(prescriptionRepository.existByPatientIdAndDoctorIdAndItems(patientid,doctorId ,prescriptionItems)) {
//                throw new RuntimeException("Prescription already exist");
//            }

            Prescription prescription = new Prescription();
            prescription.setItems(prescriptionItems);
            prescription.setDoctorId(doctorId);
            prescription.setPatientId(patientid);
            prescriptionRepository.save(prescription);

            PrescriptionResponse response = new PrescriptionResponse();
            response.setStatus(PrescriptionStatus.SUCCESS);
            response.setPrescription(prescription);
            response.setCreatedAt(LocalDateTime.now().toString());
            return response;
        }catch(Exception e){
            throw new RuntimeException("Failed to create Prescripton" , e);
        }
    }
}
