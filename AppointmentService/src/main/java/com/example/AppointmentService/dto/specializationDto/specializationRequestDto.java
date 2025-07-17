package com.example.AppointmentService.dto.specializationDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class specializationRequestDto {

    @NotNull
    private String name;
}
