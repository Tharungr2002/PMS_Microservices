package com.example.billing.billingService.Elastic;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "medicine")
public class ElasticMedicine {

    @Id
    private UUID id;

    @Field(type = FieldType.Text)
    private String name;

    private String description;

    private Double price;

    private int stockAvailable;
}
