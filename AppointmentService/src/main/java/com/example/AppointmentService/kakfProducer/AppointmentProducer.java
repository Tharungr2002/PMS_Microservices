package com.example.AppointmentService.kakfProducer;

import com.example.AppointmentBook.AppointmentBook;
import com.example.AppointmentService.Model.Appointment;
import com.example.AppointmentService.dto.PatientContact;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AppointmentProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public AppointmentProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void BookAppointment(Appointment saved, PatientContact contact) {
        AppointmentBook app = AppointmentBook.newBuilder().setDoctorname(saved.getDoctor().getName())
                .setAppointmentid(saved.getId().toString()).setEmail(contact.getEmail()).setPhoneno(contact.getPhonenumber())
                .setAppStartTime(saved.getStartingTime().toString()).setAppEndTime(saved.getEndingTime().toString()).build();
        try {
            kafkaTemplate.send("Appointment-Booking-confirm", app.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public void CancelAppointment(Appointment saved, PatientContact contact) {
        AppointmentBook app = AppointmentBook.newBuilder().setDoctorname(saved.getDoctor().getName())
                .setAppointmentid(saved.getId().toString()).setEmail(contact.getEmail()).setPhoneno(contact.getPhonenumber())
                .setAppStartTime(saved.getStartingTime().toString()).setAppEndTime(saved.getEndingTime().toString()).build();
        try {
            kafkaTemplate.send("Appointment-Booking-Cancel", app.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}