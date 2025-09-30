package com.example.billing.billingService.dto;

import com.example.billing.billingService.Enum.PrescriptionStatus;
import com.example.billing.billingService.Model.Prescription;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionResponse {

    private PrescriptionStatus status;

    private Prescription prescription;

    private String createdAt;

}
