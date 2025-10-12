package com.example.billing.billingService.KafkaProducer;

import com.example.billing.BillingPayment;
import com.example.billing.billingService.Model.Bill;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BillingPaymentService {

    private final KafkaTemplate<String , byte[]> kafkaTemplate;

    public BillingPaymentService(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void SendToBillingPayment(Bill bill ,String email, String phoneno ) {
        BillingPayment billpay = BillingPayment.newBuilder().setTotalamount(bill.getTotalAmount().toString())
                .setPaidat(bill.getPaidAt().toString()).setPaymentmethod(bill.getPaymentMethod().toString())
                .setPrescriptionid(bill.getPrescriptionId()).setEmail(email).setPhoneno(phoneno).build();

        try{
            System.out.println("---------------------check");
            kafkaTemplate.send("Billing-Payment-confirmation",billpay.toByteArray());
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
