package com.example.NotificationService.KakfaConsumer;

import com.example.AppointmentBook.AppointmentBook;
import com.example.NotificationService.Service.EmailService;
import com.example.NotificationService.Service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Appointment {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SMSService smsService;

    @KafkaListener(topics = "Appointment-Booking-confirm" , groupId = "Appointment confirmation")
    private void consumeAppConfirm(byte[] event) {
        try{
            AppointmentBook app = AppointmentBook.parseFrom(event);

            String toEmail = app.getEmail();
            String subject = "Reg Appointment Confirmation";
            String text = "Dear " + "User" + "Your Appointment Id :" + app.getAppointmentid() +"is confirmed bet" +
                    app.getAppStartTime() + "and" + app.getAppEndTime();
            emailService.sendMail(toEmail,subject,text);

            smsService.sendSMS(app.getPhoneno(), text);
        }catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @KafkaListener(topics = "Appointment-Booking-Cancel" , groupId = "Appointment Cancellation")
    private void consumeAppCancel(byte[] event) {
        try{
            AppointmentBook app = AppointmentBook.parseFrom(event);

            String toEmail = app.getEmail();
            String subject = "Reg Appointment Cancellation";
            String text = "Dear " + "User" + "Your Appointment Id :" + app.getAppointmentid() +" bet" +
                    app.getAppStartTime() + "and" + app.getAppEndTime() + "is Cancelled";
            emailService.sendMail(toEmail,subject,text);

            smsService.sendSMS(app.getPhoneno(), text);
        }catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
