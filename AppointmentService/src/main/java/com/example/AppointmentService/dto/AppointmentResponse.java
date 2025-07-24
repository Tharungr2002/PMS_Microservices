package com.example.AppointmentService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentResponse {
    private String startTime;

    private String endTime;

    private String doctorId;

    private String appointmentId;

    private String status;
}
