package com.example.billing.billingService.Elastic.Repository;

import com.example.billing.billingService.Elastic.ElasticMedicine;
import com.example.billing.billingService.Model.Medicine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.annotations.Query;

import java.util.List;
import java.util.UUID;

public interface MedicineElasticRepository extends ElasticsearchRepository<ElasticMedicine, UUID>{

    @Query("{\"match_phrase_prefix\": {\"name\": \"?0\"}}")
    List<ElasticMedicine> findByName(String name);
}
