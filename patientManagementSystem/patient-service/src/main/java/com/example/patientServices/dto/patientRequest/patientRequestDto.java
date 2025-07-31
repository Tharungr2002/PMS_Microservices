package com.example.patientServices.dto.patientRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class patientRequestDto {

    @NotBlank(message = "Email is required")
    @Email(message = "Should give valid email")
    private String email;

    @Size(max = 100, message = "size should under 30")
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "dateOfBirth is required")
    private String dateOfBirth;

    @NotBlank(message = "registeredDate")
    private String registeredDate;

    private String gender;

}
