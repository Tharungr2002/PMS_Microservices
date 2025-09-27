package com.example.AppointmentService.Repository;

import com.example.AppointmentService.Enums.SlotStatus;
import com.example.AppointmentService.Model.Doctor;
import com.example.AppointmentService.Model.Slot;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface SlotRepository extends JpaRepository<Slot, UUID> {

    @Query("select s from Slot s where s.doctor = :doctor AND" +
            "(:startTime < s.endTime AND :endTime > s.startTime)")
    List<Slot> findOverlap(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT s from Slot s where s.doctor = :doctor AND " +
    ":bookingStatus = s.slotStatus ")
    List<Slot> findByDoctorAndbookingStatus(Doctor doctor, SlotStatus bookingStatus);

    @Query("SELECT s FROM Slot s WHERE s.startTime < :currentTime AND " +
    "s.slotStatus = :bookingStatus ")
    List<Slot> findByCurrentTimeAndBookingStatus(LocalDateTime currentTime, SlotStatus bookingStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Slot s WHERE s.id = :slotuuid")
    Optional<Slot> findBySlotForUpdate(UUID slotuuid);
}
