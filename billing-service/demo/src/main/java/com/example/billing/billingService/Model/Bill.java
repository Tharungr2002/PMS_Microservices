package com.example.billing.billingService.Model;


import com.example.billing.billingService.Enum.BillStatus;
import com.example.billing.billingService.Enum.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String patientid;

    private Double TotalAmount;

    private BillStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime paidAt;

    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "bill" ,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> items;

}
