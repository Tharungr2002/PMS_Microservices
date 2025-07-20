package com.example.AppointmentService.Repository;

import com.example.AppointmentService.Model.Specialization;
import com.example.AppointmentService.dto.DoctorNameResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpecializationRepo extends JpaRepository<Specialization, UUID> {

    List<Specialization> findByDoctorId(UUID doctorId);

    List<Specialization> findByname(String name);
}
