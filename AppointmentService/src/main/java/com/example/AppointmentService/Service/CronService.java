package com.example.AppointmentService.Service;

import com.example.AppointmentService.Repository.AppointmentRepository;
import com.example.AppointmentService.dto.CronAptPatientNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CronService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public void AppointmentNotification() {

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime nextDay = LocalDateTime.now().plusHours(24);

        List<CronAptPatientNotification> AppointmentList = appointmentRepository.findAllByStartingTime(currentDateTime,nextDay);



    }
}
