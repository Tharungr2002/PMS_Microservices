package com.example.billing.billingService.Service;

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

    public List<BodyCheck> createBodyCheck(List<BodyCheckdto> bodyCheckdto) {

        List<BodyCheck> bodyCheckList = bodyCheckdto.stream().map(b->{
            BodyCheck bodyCheck =new BodyCheck();
            bodyCheck.setDescription(b.getDescription());
            bodyCheck.setName(b.getName());
            bodyCheck.setPrice(b.getPrice());
            return bodyCheck;
        }).collect(Collectors.toList());

        bodyCheckRepository.saveAll(bodyCheckList);

        return bodyCheckList;
    }
}
