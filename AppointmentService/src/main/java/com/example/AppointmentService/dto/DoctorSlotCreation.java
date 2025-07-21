package com.example.AppointmentService.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class DoctorSlotCreation {

    @NotNull
    private String startTime;

    @NotNull
    private String endTime;
}
