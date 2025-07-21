package com.example.AppointmentService.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean bookingStatus = false;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}
