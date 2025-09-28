package com.example.billing.billingService.Controller;

import com.example.billing.billingService.Model.Bill;
import com.example.billing.billingService.dto.BillCreationRequest;
import com.example.billing.billingService.dto.BillCreationResponse;
import com.example.billing.billingService.Service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping("/create")
    public ResponseEntity<BillCreationResponse> createBillForPatient(@RequestBody BillCreationRequest billCreationRequest) {
        BillCreationResponse response = billingService.createBillForPatient(billCreationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Bill> getbillByid(@PathVariable String id) {
        Bill bill = billingService.getBillByBillId(id);
        return ResponseEntity.ok(bill);
    }
}





