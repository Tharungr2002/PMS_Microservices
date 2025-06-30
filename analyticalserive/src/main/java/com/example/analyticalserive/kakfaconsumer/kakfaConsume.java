package com.example.analyticalserive.kakfaconsumer;

import com.example.patientevent.PatientEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class kakfaConsume {

    @KafkaListener(topics = "patient-event", groupId = "Analytical-service")
    public void consumePatientevent(byte[] event) {
        System.out.println("byte format event:" + event);

        try {
            PatientEvent newpatient = PatientEvent.parseFrom(event);
            System.out.println("After event:" + newpatient);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
