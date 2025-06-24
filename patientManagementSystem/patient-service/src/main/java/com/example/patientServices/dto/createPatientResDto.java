package com.example.patientServices.dto;

import com.example.patientServices.dto.billing.billingDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class createPatientResDto {
    private patientResponseDto patientresponsedto;
    private billingDto billingdto;

}
