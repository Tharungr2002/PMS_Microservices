package com.example.billing.billingService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String Description;

    private Double Amount;


}
