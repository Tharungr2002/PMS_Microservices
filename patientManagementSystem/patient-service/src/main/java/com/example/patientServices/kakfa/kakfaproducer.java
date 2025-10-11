package com.example.patientServices.kakfa;

import com.example.patientServices.Model.Patient;
import com.example.patientevent.PatientEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class kakfaproducer {

    private final KafkaTemplate<String, byte[]> kafkatemplate;

    public kakfaproducer(KafkaTemplate<String, byte[]> kafkatemplate) {
        this.kafkatemplate = kafkatemplate;
    }

    public void sendToNotServicePatientCreate(Patient patient) {
        PatientEvent newpatient = PatientEvent.newBuilder().setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setPhoneno(patient.getPhoneNumber())
                .setEventType("PATIENT_CREATED")
                .build();
        try{
            kafkatemplate.send("patient-create-eventpatient-create-event",newpatient.toByteArray());
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }


    public void sendToNotServicePatientDelete(Patient patient) {
        PatientEvent newpatient = PatientEvent.newBuilder().setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setPhoneno(patient.getPhoneNumber())
                .setEventType("PATIENT_CREATED")
                .build();
        try{
            kafkatemplate.send("patient-delete-event",newpatient.toByteArray());
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
