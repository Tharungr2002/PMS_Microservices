package com.example.AppointmentService.Repository;

import com.example.AppointmentService.Enums.AppointmentStatus;
import com.example.AppointmentService.Model.Appointment;
import com.example.AppointmentService.dto.AppointmentBooked;
import com.example.AppointmentService.dto.PatientBookingConflict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    //It does not need select new coz jpa capapble to map into interface
    @Query("SELECT a.patientId as patientId , a.startingTime as startTime, a.endingTime as endTime " +
    "FROM Appointment a " +
    "WHERE a.patientId = :patientId " +
   "AND a.status = :status " +
    "AND (a.startingTime < :endTime AND a.endingTime > :startTime)")
    public PatientBookingConflict findbypatientIdAndstatus(UUID patientId, AppointmentStatus status , LocalDateTime startTime , LocalDateTime endTime);


    //select is neeeded coz jpa dont map into class
    @Query("SELECT new com.example.AppointmentService.dto.AppointmentBooked (" +
            "a.doctor.name , a.id , a.startingTime , a.endingTime)" +
    "FROM Appointment a " +
    "WHERE a.patientId = :patientId AND a.status = :status")
    public List<AppointmentBooked> findByPatientId(UUID patientId , AppointmentStatus status);

}
