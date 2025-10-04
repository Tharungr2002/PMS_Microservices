package com.example.billing.billingService.Controller;

import com.example.billing.billingService.Model.Medicine;
import com.example.billing.billingService.Service.MedicineService;
import com.example.billing.billingService.dto.AddMedicineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping("/create")
    public ResponseEntity<List<Medicine>> addMedicine(@RequestBody List<AddMedicineDto> addMedicineDto) {

        List<Medicine> response = medicineService.addMedicine(addMedicineDto);
        return ResponseEntity.ok(response);
    }
}
