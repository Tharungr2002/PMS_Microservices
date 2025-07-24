package com.example.AppointmentService.Model;

import com.example.AppointmentService.Enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;

    private UUID patientId;

    private LocalDateTime startingTime;

    private LocalDateTime endingTime;

    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
}
