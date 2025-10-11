package com.example.NotificationService.KakfaConsumer;

import com.example.Notification.BillingPayment;
import com.example.NotificationService.Service.EmailService;
import com.example.NotificationService.Service.SMSService;
import com.example.patientevent.PatientEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Billing {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SMSService smsService;

    @KafkaListener(topics = "Billing-Payment-confirmation", groupId = "Bill-payment-confirm")
    public void ConsumeCreatePatient(byte[] event) {

        try{
            BillingPayment bill = BillingPayment.parseFrom(event);

            String toEmail = bill.getEmail();
            String subject = "Reg Paymeny for Bill";
            String text = "Dear "  + "Your prescription Id :" + bill.getPrescriptionid()
                     +"is paid at" + bill.getPaidat() + "by paymentMethod" + bill.getPaymentmethod();
            emailService.sendMail(toEmail,subject,text);

            smsService.sendSMS(bill.getPhoneno(), text);
        }catch(Exception e) {
            throw new RuntimeException("Not able to parse event", e);
        }

    }
}
