package com.example.billing.billingService.Service;

import com.example.billing.billingService.Enum.BillStatus;
import com.example.billing.billingService.Enum.PaymentMethod;
import com.example.billing.billingService.KafkaProducer.BillingPaymentService;
import com.example.billing.billingService.Model.*;
import com.example.billing.billingService.Repository.*;
import com.example.billing.billingService.WebClient.PatientContactApi;
import com.example.billing.billingService.dto.*;
import com.example.billing.billingService.mapper.BillMapper;
import com.google.j2objc.annotations.AutoreleasePool;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BillingService {

    @Autowired
    private PatientContactApi patientContactApi;

    @Autowired
    private BillingPaymentService billingPayment;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private BillItemRepo billItemRepo;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PrescriptionItemRepository prescriptionItemRepository;

    @Transactional
    public BillCreationResponse createBillForPatient(BillCreationRequest billCreationRequest, String prescriptionId) {

        UUID patientid = UUID.fromString(billCreationRequest.getPatientid());

        Prescription prescription = prescriptionRepository.findById(UUID.fromString(prescriptionId))
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        //from db
        List<PrescriptionItem> prescriptionItems = prescription.getItems();

        //from db convert to map
        Map<String, PrescriptionItem> prescriptionItemMap = prescriptionItems.stream()
                .collect(Collectors.toMap(PrescriptionItem::getItemId , prescriptionItem-> prescriptionItem));

        //from request
        List<Items> items = billCreationRequest.getItems();

        //duplicate check if not make them true
        List<PrescriptionItem>  presItem = items.stream().map(i->{
            PrescriptionItem item =  prescriptionItemMap.get(i.getMedicineId());
            if(item == null) {throw new RuntimeException("Invalid medicineId: " + i.getMedicineId()); }

            if(item.isBillCreated()) {throw new RuntimeException("Bill is already created for this " + i.getName());}
            item.setBillCreated(true);
            return item;
        }).toList();

        prescriptionItemRepository.saveAll(presItem);

        List<BillItem> BillIntems =BillMapper.billRequestToBill(patientid,billCreationRequest.getItems() );
        billItemRepo.saveAll(BillIntems);

        Double TotalAmount = billCreationRequest.getItems().stream().mapToDouble(c->c.getAmount()*c.getQuantity()).sum();

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

    @Transactional
    public Bill updateBill(String billId, String method) {

        UUID billUUID = UUID.fromString(billId);
        Bill bill = billRepository.findById(billUUID).orElseThrow(()-> new RuntimeException("bill does not exists"));

        if(bill.getStatus() == BillStatus.COMPLETED) {
            throw new RuntimeException("Already payment complted for this bill!");
        }

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

                b.setPaidStatus(true);

                int availableStock = medicine.getStockAvailable();

                if (availableStock < b.getQuantity()) {
                    throw new RuntimeException("Stock not available !! please check again for " +
                            b.getName());
                }

                int updatedstock = availableStock - b.getQuantity();
                System.out.println("-----------------"+ availableStock + " " + b.getQuantity() );

                medicine.setStockAvailable(updatedstock);
                medicineRepository.save(medicine);
            }

        });

        bill.setItems(billList);
        Bill res = billRepository.save(bill);

        PatientContact response = patientContactApi.getContact(bill.getPatientid());
        //payment update to patient via mail.
        billingPayment.SendToBillingPayment(bill , response.getEmail(), response.getPhonenumber());

        return res;

    }

    public List<Bill> getAllBills(String prescriptionId) {
       List<Bill> response =  billRepository.findAllByPrescriptionId(prescriptionId);

       return response;
    }
}
