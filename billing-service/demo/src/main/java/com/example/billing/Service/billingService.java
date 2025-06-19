package com.example.billing.Service;

import com.example.billing.dto.billingDto;
import org.springframework.stereotype.Service;

@Service
public class billingService {
    public static billingDto createbilling(String id) {
        return new billingDto(id,1000);
    }
}
