package com.example.billing.billingService.Controller;

import com.example.billing.billingService.Model.Bill;
import com.example.billing.billingService.dto.BillCreationRequest;
import com.example.billing.billingService.dto.BillCreationResponse;
import com.example.billing.billingService.Service.BillingService;
import com.example.billing.billingService.dto.patientBill;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping("admin/create")
    public ResponseEntity<BillCreationResponse> createBillForPatient(@RequestBody BillCreationRequest billCreationRequest,
                                                                     @RequestHeader("X-prescriptionId") String prescriptionId) {
        BillCreationResponse response = billingService.createBillForPatient(billCreationRequest,prescriptionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("admin/get/{id}")
    public ResponseEntity<Bill> getbillByid(@PathVariable String id) {
        Bill bill = billingService.getBillByBillId(id);
        return ResponseEntity.ok(bill);
    }

    @GetMapping("admin/get/patient/{patientid}")
    public ResponseEntity<List<patientBill>> getAllBillForPatients(@PathVariable String patientid) {
        List<patientBill> response = billingService.getAllBillForPatients(patientid);
        return ResponseEntity.ok(response);
    }

    @PutMapping("admin/update/payment/{billId}/{method}")
    public ResponseEntity<Bill> updatePaymentForBill(@PathVariable String billId , @PathVariable String method) {
        Bill bill = billingService.updateBill(billId,method);
        return ResponseEntity.ok(bill);
    }

    @GetMapping("admin/get/billforprescription/{prescriptionId}")
    public ResponseEntity<List<Bill>> getAllBillForPrescription(@PathVariable String prescriptionId) {

        List<Bill> AllBills = billingService.getAllBills(prescriptionId);
        return ResponseEntity.ok(AllBills);
    }
}





