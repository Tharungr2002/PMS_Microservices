package com.example.AppointmentService.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PatientBookingConflict {

     UUID getPatientId();

     LocalDateTime getStartTime();

     LocalDateTime getEndTime();
}
