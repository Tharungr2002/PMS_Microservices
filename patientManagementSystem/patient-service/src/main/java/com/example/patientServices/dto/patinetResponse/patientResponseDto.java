package com.example.patientServices.dto.patinetResponse;

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
    private String phoneNumber;
}
