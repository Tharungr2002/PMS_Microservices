package com.example.billing.billingService.Repository;

import com.example.billing.billingService.Model.BodyCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BodyCheckRepository extends JpaRepository<BodyCheck, UUID> {

}
