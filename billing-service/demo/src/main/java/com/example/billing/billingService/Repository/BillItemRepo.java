package com.example.billing.billingService.Repository;

import com.example.billing.billingService.Model.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillItemRepo extends JpaRepository<BillItem, UUID> {
}
