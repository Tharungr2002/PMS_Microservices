package com.example.billing.billingService.mapper;

import com.example.billing.billingService.Enum.BillStatus;
import com.example.billing.billingService.Model.Bill;
import com.example.billing.billingService.Model.BillItem;
import com.example.billing.billingService.dto.BillCreationResponse;
import com.example.billing.billingService.dto.Items;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class BillMapper {

    public static List<BillItem> billRequestToBill(UUID patientid , List<Items> items) {

        List<BillItem> billItem = items.stream().map(i->{
                    BillItem billing = new BillItem();

                    billing.setType(i.getType());
                    billing.setName(i.getName());
                    billing.setQuantity(i.getQuantity());
                    billing.setMedicineId(i.getMedicineId());
                    billing.setDescription(i.getDescription());
                    billing.setAmount(i.getAmount());
                    return billing;
                }
                ).collect(Collectors.toList());
        return billItem;
    }

    public static BillCreationResponse createBillMapperToResponse(Bill bill) {

        BillCreationResponse billCreationResponse = new BillCreationResponse();

        billCreationResponse.setBillId(bill.getId().toString());
        billCreationResponse.setDateCreated(bill.getCreatedAt().toString());
        billCreationResponse.setTotalAmount(bill.getTotalAmount());

        List<Items> items = bill.getItems().stream().map(i->{
            Items item = new Items();
            item.setType(i.getType());
            item.setQuantity(i.getQuantity());
            item.setMedicineId(i.getMedicineId());
            item.setName(i.getName());
            item.setAmount(i.getAmount());
            item.setDescription(i.getDescription());
            return item;
        }).toList();

        billCreationResponse.setItems(items);

        return billCreationResponse;
    }
}
