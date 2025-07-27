package com.example.NotificationService.Controller;

import com.example.NotificationService.Service.AppointmentEmailService;
import com.example.NotificationService.Service.AppointmentSMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private AppointmentEmailService emailService;

    @Autowired
    private AppointmentSMSService SMSService;

    @GetMapping("/mail/send/{email}")
    public void sendEmail(@PathVariable String email) {
        emailService.sendMail(email,"Reg your appointment" ,
                "Your appointment has been successfully scheduled at 27/07/2025 with id : 6144caea-2bda-4797-bcfd-3a6d6be41a05.\n\n\n\nPlease note: This is an automated message. Do not reply to this email.");
    }

    @GetMapping("/sms/{phoneno}")
    public void sendSMS(@PathVariable String phoneno) {
        SMSService.sendSMS(phoneno, "Your appointment has been successfully scheduled at 27/07/2025 with id : 6144caea-2bda-4797-bcfd-3a6d6be41a05:)");
    }
}
