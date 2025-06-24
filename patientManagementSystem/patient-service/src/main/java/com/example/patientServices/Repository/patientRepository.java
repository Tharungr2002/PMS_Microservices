package com.example.patientServices.Repository;

import com.example.patientServices.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface patientRepository extends JpaRepository<Patient, UUID> {

    boolean existsByEmail(String email);

}
