package com.example.AppointmentService.Service;

import com.example.AppointmentService.Model.specializationDb;
import com.example.AppointmentService.Repository.specializationDbRepo;
import com.example.AppointmentService.dto.specializationDbResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class specializationService {

    @Autowired
    private specializationDbRepo specializationdbrepo;

    public specializationDb addSpecialization(specializationDb specializationdb) {

        if(specializationdbrepo.existsByName(specializationdb.getName())) {
            throw new RuntimeException("Specialization Already exists");
        }
        specializationDb newSpec = specializationdbrepo.save(specializationdb);
        return newSpec;
    }

    public List<specializationDbResponse> getAll() {

        List<specializationDbResponse> allSpec = specializationdbrepo.findAll().stream()
                .map(
                        s-> {
                            specializationDbResponse indSpec = new specializationDbResponse();
                            indSpec.setName(s.getName());
                            return indSpec;
                        }).collect(Collectors.toList());
        return allSpec;

    }
}
