package com.example.billing.billingService.Repository;

import com.example.billing.billingService.Model.Bill;
import com.example.billing.billingService.dto.patientBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BillRepository extends JpaRepository<Bill, UUID> {

    @Query("SELECT b.id as billId " +
            "FROM Bill b " +
            "WHERE b.patientid = :patientUUID ")
    List<patientBill> findAllBillByPatient(String patientUUID);
}
