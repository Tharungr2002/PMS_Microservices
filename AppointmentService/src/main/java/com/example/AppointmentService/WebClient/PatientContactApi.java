package com.example.AppointmentService.WebClient;

import com.example.AppointmentService.dto.PatientContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PatientContactApi {

    @Autowired
    private WebClient webclient;

    public PatientContact getContact(String patientid) {
        return webclient.get().uri("http://localhost:4000/patients/admin/getcontact").
                header("X-Patient-id",patientid).retrieve()
                .bodyToMono(PatientContact.class).block();
    }
}
