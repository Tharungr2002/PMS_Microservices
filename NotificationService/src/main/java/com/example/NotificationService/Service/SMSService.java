package com.example.NotificationService.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SMSService {

    @Value("${twilio.phoneNumber}")
    private String fromNumber;

    public void sendSMS(String to, String message) {
        Message.creator( new PhoneNumber(to),
                new PhoneNumber(fromNumber),
                message).create();
    }
}
