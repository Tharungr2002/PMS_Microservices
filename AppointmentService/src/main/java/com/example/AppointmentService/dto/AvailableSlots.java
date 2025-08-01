package com.example.AppointmentService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AvailableSlots {

    private String startingTime;

    private String endingTime;

    private String slotId;

    private String doctorId;

}
