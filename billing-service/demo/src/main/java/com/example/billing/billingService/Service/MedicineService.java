package com.example.billing.billingService.Service;

import com.example.billing.billingService.Model.Medicine;
import com.example.billing.billingService.Repository.MedicineRepository;
import com.example.billing.billingService.dto.AddMedicineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    public List<Medicine> addMedicine(List<AddMedicineDto> addMedicineDto) {

        List<Medicine> ListMedicine = addMedicineDto.stream().map(
                m->{
                    Medicine medicine = new Medicine();
                    medicine.setStockAvailable(m.getStockAvailable());
                    medicine.setDescription(m.getDescription());
                    medicine.setName(m.getName());
                    medicine.setPrice(m.getPrice());
                    return medicine;
                }
        ).collect(Collectors.toList());

        medicineRepository.saveAll(ListMedicine);

        return ListMedicine;
    }
}
