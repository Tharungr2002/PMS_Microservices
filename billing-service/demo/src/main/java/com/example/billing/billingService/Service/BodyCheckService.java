package com.example.billing.billingService.Service;

import com.example.billing.billingService.Elastic.ElasticModel.ElasticBodyCheck;
import com.example.billing.billingService.Elastic.Repository.BodyCheckElasticRepository;
import com.example.billing.billingService.Model.BodyCheck;
import com.example.billing.billingService.Repository.BodyCheckRepository;
import com.example.billing.billingService.dto.BodyCheckdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BodyCheckService {

    @Autowired
    private BodyCheckRepository bodyCheckRepository;

    @Autowired
    private BodyCheckElasticRepository bodyCheckElasticRepository;

    public List<BodyCheck> createBodyCheck(List<BodyCheckdto> bodyCheckdto) {

        List<BodyCheck> bodyCheckList = bodyCheckdto.stream().map(b->{
            BodyCheck bodyCheck =new BodyCheck();
            bodyCheck.setDescription(b.getDescription());
            bodyCheck.setName(b.getName());
            bodyCheck.setPrice(b.getPrice());
            return bodyCheck;
        }).collect(Collectors.toList());

        List<BodyCheck> response = bodyCheckRepository.saveAll(bodyCheckList);

        List<ElasticBodyCheck> BodyCheckList = response.stream().map(b->{
            ElasticBodyCheck es = new ElasticBodyCheck();

            es.setDescription(b.getDescription());
            es.setId(b.getId());
            es.setPrice(b.getPrice());
            es.setName(b.getName());
            return es;
        }).collect(Collectors.toList());

        bodyCheckElasticRepository.saveAll(BodyCheckList);

        return response;
    }

    public List<ElasticBodyCheck> searchBodyCheck(String name) {

        return bodyCheckElasticRepository.findByName(name);
    }
}
