package com.example.billing.Controller;

import com.example.billing.Service.billingService;
import com.example.billing.dto.billingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/billing")
@RestController
public class billingController {

    @Autowired
    private billingService billinservice;

    @GetMapping("/{id}")
    public ResponseEntity<billingDto> billingDetails(@PathVariable String id) {
        billingDto billingdto = billingService.createbilling(id);
        return ResponseEntity.ok().body(billingdto);
    }
}
