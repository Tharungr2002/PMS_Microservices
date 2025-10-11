package com.example.NotificationService.KakfaConsumer;

import com.example.NotificationService.Service.EmailService;
import com.example.NotificationService.Service.SMSService;
import com.example.patientevent.PatientEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Patient {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SMSService smsService;

    @KafkaListener(topics = "patient-create-event", groupId = "Patient-create")
    public void ConsumeCreatePatient(byte[] event) {

        try{
            PatientEvent patient = PatientEvent.parseFrom(event);

            String toEmail = patient.getEmail();
            String subject = "Reg Create Patient Id";
            String text = "Dear " + patient.getName() + "Your patient Id :" + patient.getPatientId() +"is created";
            emailService.sendMail(toEmail,subject,text);

            smsService.sendSMS(patient.getPhoneno(), text);
        }catch(Exception e) {
            throw new RuntimeException("Not able to parse event", e);
        }

    }

    @KafkaListener(topics = "patient-delete-event", groupId = "Patient-delete")
    public void ConsumeDeletePatient(byte[] event) {

        try{
            PatientEvent patient = PatientEvent.parseFrom(event);

            String toEmail = patient.getEmail();
            String subject = "Reg delete Patient Id";
            String text = "Dear " + patient.getName() + "Your patient Id :" + patient.getPatientId() +"is deleted";
            emailService.sendMail(toEmail,subject,text);

            smsService.sendSMS(patient.getPhoneno(), text);
        }catch(Exception e) {
            throw new RuntimeException("Not able to parse event", e);
        }

    }


}
