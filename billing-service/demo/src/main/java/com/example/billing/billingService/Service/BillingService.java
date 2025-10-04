package com.example.billing.billingService.Service;

import com.example.billing.billingService.Enum.BillStatus;
import com.example.billing.billingService.Enum.PaymentMethod;
import com.example.billing.billingService.Model.Bill;
import com.example.billing.billingService.Model.Medicine;
import com.example.billing.billingService.Repository.BillItemRepo;
import com.example.billing.billingService.Repository.BillRepository;
import com.example.billing.billingService.Repository.MedicineRepository;
import com.example.billing.billingService.dto.BillCreationRequest;
import com.example.billing.billingService.dto.BillCreationResponse;
import com.example.billing.billingService.Model.BillItem;
import com.example.billing.billingService.dto.Items;
import com.example.billing.billingService.dto.patientBill;
import com.example.billing.billingService.mapper.BillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BillingService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillItemRepo billItemRepo;

    @Autowired
    private MedicineRepository medicineRepository;

    public BillCreationResponse createBillForPatient(BillCreationRequest billCreationRequest) {

        UUID patientid = UUID.fromString(billCreationRequest.getPatientid());
        Double TotalAmount = billCreationRequest.getItems().stream().mapToDouble(c->c.getAmount()*c.getQuantity()).sum();

        List<BillItem> BillIntems =BillMapper.billRequestToBill(patientid,billCreationRequest.getItems());
        billItemRepo.saveAll(BillIntems);


        Bill bill = new Bill();
        bill.setPrescriptionId(billCreationRequest.getPrescriptionId());
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

    public List<patientBill> getAllBillForPatients(String patientid) {

        List<patientBill> billIds = billRepository.findAllBillByPatient(patientid);

        return billIds;
    }

    public Bill updateBill(String billId, String method) {

        UUID billUUID = UUID.fromString(billId);
        Bill bill = billRepository.findById(billUUID).orElseThrow(()-> new RuntimeException("bill does not exists"));

        PaymentMethod paymethod = PaymentMethod.valueOf(method);

        bill.setPaidAt(LocalDateTime.now());
        bill.setStatus(BillStatus.COMPLETED);
        bill.setPaymentMethod(paymethod);

        List<BillItem> billList = bill.getItems();

        System.out.println("------------"+billList.toString() );

        //Updating stock after billing
        billList.stream().forEach(b-> {

            if("MEDICINE".equals(b.getType())) {
                Medicine medicine = medicineRepository.findById(UUID.fromString(b.getMedicineId()))
                        .orElseThrow(() -> new RuntimeException("medicine did not found"));

                int availableStock = medicine.getStockAvailable();

                if (availableStock < b.getQuantity()) {
                    throw new RuntimeException("Stock not available !! please check again");
                }

                int updatedstock = availableStock - b.getQuantity();
                System.out.println("-----------------"+ availableStock + " " + b.getQuantity() );

                medicine.setStockAvailable(updatedstock);
                medicineRepository.save(medicine);
            }

        });


        return billRepository.save(bill);

    }

    public List<Bill> getAllBills(String prescriptionId) {
       List<Bill> response =  billRepository.findAllByPrescriptionId(prescriptionId);

       return response;
    }
}
