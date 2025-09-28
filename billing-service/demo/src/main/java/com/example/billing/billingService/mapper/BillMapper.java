package com.example.billing.billingService.mapper;

import com.example.billing.billingService.Enum.BillStatus;
import com.example.billing.billingService.dto.BillCreationResponse;
import com.example.billing.billingService.dto.Items;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public class BillMapper {
    public static BillCreationResponse createBillMapper(UUID id, LocalDateTime createdAt, Double totalAmount, BillStatus status, List<Items> items) {

        BillCreationResponse billCreationResponse = new BillCreationResponse();

        billCreationResponse.setBillId(id.toString());
        billCreationResponse.setDateCreated(createdAt.toString());
        billCreationResponse.setTotalAmount(totalAmount);
        billCreationResponse.setItems(items);

        return billCreationResponse;
    }
}
