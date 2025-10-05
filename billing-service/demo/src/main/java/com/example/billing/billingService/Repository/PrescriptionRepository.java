package com.example.billing.billingService.Repository;

import com.example.billing.billingService.Model.Prescription;
import com.example.billing.billingService.Model.PrescriptionItem;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {


    boolean existsByAppointmentId(String appointmentId);
}
