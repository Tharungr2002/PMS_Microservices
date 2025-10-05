package com.example.billing.billingService.Elastic.Repository;

import com.example.billing.billingService.Elastic.ElasticModel.ElasticMedicine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.annotations.Query;

import java.util.List;
import java.util.UUID;

public interface MedicineElasticRepository extends ElasticsearchRepository<ElasticMedicine, UUID>{

    @Query("{\"wildcard\": {\"name\": {\"value\": \"*?0*\"}}}")
    List<ElasticMedicine> findByName(String name);
}
