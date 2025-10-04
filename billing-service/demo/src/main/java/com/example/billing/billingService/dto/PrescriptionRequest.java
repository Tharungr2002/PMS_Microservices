package com.example.billing.billingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionRequest {

    private String type;

    private String medicineName;

    private String Dosage;

   private String itemId;

    private int quantity;
}
