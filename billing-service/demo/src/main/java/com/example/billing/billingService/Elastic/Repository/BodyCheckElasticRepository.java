package com.example.billing.billingService.Elastic.Repository;


import com.example.billing.billingService.Elastic.ElasticModel.ElasticBodyCheck;
import com.example.billing.billingService.Elastic.ElasticModel.ElasticMedicine;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BodyCheckElasticRepository extends ElasticsearchRepository<ElasticBodyCheck, String> {

    @Query("{\"wildcard\": {\"name\": {\"value\": \"*?0*\"}}}")
    List<ElasticBodyCheck> findByName(String name);
}
