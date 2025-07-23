package com.example.AppointmentService.Repository;

import com.example.AppointmentService.Model.specializationDb;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpecializationDbRepo extends JpaRepository<specializationDb, UUID> {

    boolean existsByName(@NotNull String name);
}
