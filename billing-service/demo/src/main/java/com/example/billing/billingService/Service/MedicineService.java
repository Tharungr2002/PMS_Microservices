package com.example.billing.billingService.Service;

import com.example.billing.billingService.Elastic.ElasticMedicine;
import com.example.billing.billingService.Elastic.Repository.MedicineElasticRepository;
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

    @Autowired
    private MedicineElasticRepository medicineElasticRepository;

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

        List<Medicine> savedMedicines = medicineRepository.saveAll(ListMedicine);

        List<ElasticMedicine> searchList = savedMedicines.stream().map(s-> {
            ElasticMedicine es = new ElasticMedicine();

            es.setId(s.getId());
            es.setDescription(s.getDescription());
            es.setName(s.getName());
            es.setPrice(s.getPrice());
            es.setStockAvailable(s.getStockAvailable());
            return es;
        }).collect(Collectors.toList());

        medicineElasticRepository.saveAll(searchList);

        return savedMedicines;
    }

    public List<ElasticMedicine> getAllMedicine(String name) {

        return medicineElasticRepository.findByName(name.toLowerCase());
    }
}
