package com.example.billing.billingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Items {

    private String Description;

    private Double Amount;

    private int quantity;

    private String medicineId;

    private String name;
}
