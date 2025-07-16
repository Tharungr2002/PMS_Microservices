package com.example.AppointmentService.dto.doctorDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class doctorDto {

    private String loginId;

    private List<String> specialization;

    private String phoneNumber;

    private String yearOfExperience;


}
