package com.example.AppointmentService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentBooked {

    private String name;

    private UUID AppointmentID;

    private LocalDateTime startingTime;

    private LocalDateTime endingTime;

}
