package com.example.billing.billingService.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.build.AllowNonPortable;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BodyCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String description;

    private Double price;
}
