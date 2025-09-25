package com.example.AppointmentService.Repository;

import com.example.AppointmentService.Model.Doctor;
import com.example.AppointmentService.Model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public interface SlotRepository extends JpaRepository<Slot, UUID> {

    @Query("select s from Slot s where s.doctor = :doctor AND" +
            "(:startTime < s.endTime AND :endTime > s.startTime)")
    List<Slot> findOverlap(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT s from Slot s where s.doctor = :doctor AND " +
    ":bookingStatus = s.bookingStatus ")
    List<Slot> findByDoctorAndbookingStatus(Doctor doctor, boolean bookingStatus);
}
