package com.example.billing.billingService.Controller;

import com.example.billing.billingService.Model.BodyCheck;
import com.example.billing.billingService.Repository.BodyCheckRepository;
import com.example.billing.billingService.Service.BodyCheckService;
import com.example.billing.billingService.dto.BodyCheckdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bodycheck")
public class BodyCheckController {

    @Autowired
    private BodyCheckService bodyCheckService;

    @PostMapping("/create")
    public ResponseEntity<List<BodyCheck>> addbodycheck(@RequestBody List<BodyCheckdto> bodyCheckdto) {
        System.out.println("---------------"+ bodyCheckdto.toString());
        List<BodyCheck> response = bodyCheckService.createBodyCheck(bodyCheckdto);
        return ResponseEntity.ok(response);
    }
}
