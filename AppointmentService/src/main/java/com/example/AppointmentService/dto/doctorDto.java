package com.example.AppointmentService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class doctorDto {

    private String loginId;

    private List<specializationRequestDto> specializations;

    private String phoneNumber;

    private String yearOfExperience;

}
