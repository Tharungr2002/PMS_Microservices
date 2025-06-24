package com.example.patientServices.dto;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class patientResponseDto {

    private String name;
    private String email;
    private String id;
    private String address;
    private String dateOfBirth;
}
