package com.example.billing.billingService.Service;

import com.example.billing.billingService.Enum.BillStatus;
import com.example.billing.billingService.Model.Bill;
import com.example.billing.billingService.Repository.BillItemRepo;
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

    @Autowired
    private BillItemRepo billItemRepo;

    public BillCreationResponse createBillForPatient(BillCreationRequest billCreationRequest) {

        UUID patientid = UUID.fromString(billCreationRequest.getPatientid());
        Double TotalAmount = billCreationRequest.getItems().stream().mapToDouble(c->c.getAmount()).sum();

        List<BillItem> BillIntems =BillMapper.billRequestToBill(patientid,billCreationRequest.getItems());
        billItemRepo.saveAll(BillIntems);


        Bill bill = new Bill();
        bill.setPatientid(patientid.toString());
        bill.setCreatedAt(LocalDateTime.now());
        bill.setStatus(BillStatus.PENDING);
        bill.setTotalAmount(TotalAmount);
        bill.setItems(BillIntems);
        Bill finalbill = billRepository.save(bill);

        return BillMapper.createBillMapperToResponse(finalbill);

    }

    public Bill getBillByBillId(String id) {

        UUID billUUID = UUID.fromString(id);

        Bill bill = billRepository.findById(billUUID).orElseThrow(()->  new RuntimeException("Bill does not found"));

        return bill;
    }
}
