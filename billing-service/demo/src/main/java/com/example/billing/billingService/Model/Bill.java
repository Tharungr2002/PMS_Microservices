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

    private BillStatus status = BillStatus.PENDING;

    private LocalDateTime createdAt;

    private LocalDateTime paidAt;

    private PaymentMethod paymentMethod;

    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true)
    @JoinColumn(name = "bill_id")                                            //bill id created in billitem
    private List<BillItem> items;

    private String prescriptionId;

}
