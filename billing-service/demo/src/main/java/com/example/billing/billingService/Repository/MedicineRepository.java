package com.example.billing.billingService.Repository;

import com.example.billing.billingService.Model.Medicine;
import org.checkerframework.common.util.count.report.qual.ReportCreation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, UUID> {


    boolean existsByName(String name);
}
