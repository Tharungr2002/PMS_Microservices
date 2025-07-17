package com.example.AppointmentService.dto.doctorDto;

import com.example.AppointmentService.Model.Specialization;
import com.example.AppointmentService.dto.specializationDto.specializationRequestDto;
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
