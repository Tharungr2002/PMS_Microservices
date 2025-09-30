package com.example.billing.billingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillCreationResponse {

    private String billId;

    private String dateCreated;

    private Double TotalAmount;

    private List<Items> items;



}
