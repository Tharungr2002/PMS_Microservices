package com.example.billing.billingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMedicineDto {

    private String name;

    private String description;

    private Double price;

    private int stockAvailable;

}
