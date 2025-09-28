package com.example.billing.billingService.Service;

import com.example.billing.billingService.Enum.BillStatus;
import com.example.billing.billingService.Model.Bill;
import com.example.billing.billingService.Repository.BillRepository;
import com.example.billing.billingService.dto.BillCreationRequest;
import com.example.billing.billingService.dto.BillCreationResponse;
import com.example.billing.billingService.Model.BillItem;
import com.example.billing.billingService.mapper.BillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BillingService {

    @Autowired
    private BillRepository billRepository;

    public BillCreationResponse createBillForPatient(BillCreationRequest billCreationRequest) {

        UUID patientid = UUID.fromString(billCreationRequest.getPatientid());

       List<BillItem> Billitems =  billCreationRequest.getItems().stream().map(i->
       {
           BillItem items = new BillItem();
           items.setAmount(i.getAmount());
           items.setDescription(i.getDescription());
           return items;
       }).collect(Collectors.toList());

        Double TotalAmount = billCreationRequest.getItems().stream().mapToDouble(c->c.getAmount()).sum();

        Bill bill = new Bill();
        bill.setPatientid(patientid.toString());
        bill.setCreatedAt(LocalDateTime.now());
        bill.setItems(Billitems);
        bill.setStatus(BillStatus.PENDING);
        bill.setTotalAmount(TotalAmount);

        Bill billing = billRepository.save(bill);

        return BillMapper.createBillMapper(billing.getId(),billing.getCreatedAt(),billing.getTotalAmount(),billing.getStatus(),billCreationRequest.getItems());

    }
}
