package com.example.AppointmentService.Repository;

import com.example.AppointmentService.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface repo extends JpaRepository<Doctor, UUID> {

    boolean existsByLoginId(UUID loginId);

    Doctor findByLoginId(UUID loginId);

    void deleteByloginId(UUID loginId);

    Doctor findByname(String name);

}
