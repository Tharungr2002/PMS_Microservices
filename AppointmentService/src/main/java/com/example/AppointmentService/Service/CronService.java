package com.example.AppointmentService.Service;

import com.example.AppointmentService.Repository.AppointmentRepository;
import com.example.AppointmentService.dto.CronAptPatientNotification;
import com.example.AppointmentService.dto.PatientContacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.reactive.function.client.WebClient;

import static java.util.stream.Collectors.toList;


@Service
public class CronService {

    @Autowired
    private WebClient webclient;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public void AppointmentNotification() {

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime nextDay = LocalDateTime.now().plusHours(24);

        List<CronAptPatientNotification> AppointmentList = appointmentRepository.findAllByStartingTime(currentDateTime,nextDay);

        List<PatientContacts> patientContactsList = webclient.post().uri("http://localhost:4000/patients/getAll/contacts")
                .bodyValue(AppointmentList).retrieve().bodyToFlux(PatientContacts.class).collectList().block();

        Map<UUID,PatientContacts> patientContactsMap = patientContactsList.stream().
                collect(Collectors.toMap(PatientContacts::getPatientId , patientContacts -> patientContacts));

        List <PatientContacts> response = AppointmentList.stream().map(
                apt->{
                    PatientContacts contacts = patientContactsMap.get(apt.getPatientId());

                    String message = "your apt with doctor" + " " + apt.getDoctorName() + "is scheduled on " + apt.getStartingTime();

                    contacts.setMessage(message);

                    //--------------------Send Contacts obj to kakfa (Not Service)------------------------

                    return contacts;
                }
        ).toList();

        response.forEach(System.out::println);






    }
}
