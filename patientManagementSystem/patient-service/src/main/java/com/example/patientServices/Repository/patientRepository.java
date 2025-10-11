package com.example.patientServices.Repository;

import com.example.patientServices.Model.Patient;
import com.example.patientServices.dto.patinetResponse.PatientContact;
import com.example.patientServices.dto.patinetResponse.PatientContacts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface patientRepository extends JpaRepository<Patient, UUID> {

    boolean existsByEmail(String email);

    Page<Patient> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT new com.example.patientServices.dto.patinetResponse.PatientContacts (" +
            "p.email , p.phoneNumber , p.id ) " +
    "FROM Patient p " +
    "WHERE p.id IN :uuids ")
    List<PatientContacts> findAllByIds(List<UUID> uuids);

    @Query("SELECT new com.example.patientServices.dto.patinetResponse.PatientContact (" +
            "p.email, p.phoneNumber ) " +
            "FROM Patient p " +
            "WHERE p.id = :uuid ")
    PatientContact findByIdCustom(UUID uuid);
}
