package com.example.patientServices.dto.patinetResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientContact {

   private String email;

   private String phonenumber;
}
