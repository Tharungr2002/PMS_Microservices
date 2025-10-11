package com.example.billing.billingService.Controller;

import com.example.billing.billingService.Elastic.ElasticModel.ElasticBodyCheck;
import com.example.billing.billingService.Elastic.ElasticModel.ElasticMedicine;
import com.example.billing.billingService.Model.BodyCheck;
import com.example.billing.billingService.Repository.BodyCheckRepository;
import com.example.billing.billingService.Service.BodyCheckService;
import com.example.billing.billingService.dto.BodyCheckdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/inventory/bodycheck")
public class BodyCheckController {

    @Autowired
    private BodyCheckService bodyCheckService;

    @PostMapping("/create")
    public ResponseEntity<List<BodyCheck>> addbodycheck(@RequestBody List<BodyCheckdto> bodyCheckdto) {
        List<BodyCheck> response = bodyCheckService.createBodyCheck(bodyCheckdto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ElasticBodyCheck>> searchBodyCheck(@RequestParam String name) {

        List<ElasticBodyCheck> response = bodyCheckService.searchBodyCheck(name);
        return ResponseEntity.ok(response);
    }
}
