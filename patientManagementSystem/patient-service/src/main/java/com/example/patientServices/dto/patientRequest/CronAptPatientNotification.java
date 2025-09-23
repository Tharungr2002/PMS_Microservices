package com.example.patientServices.dto.patientRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CronAptPatientNotification {

    private String doctorName;

    private LocalDateTime startingTime;

    private UUID patientId;
}
