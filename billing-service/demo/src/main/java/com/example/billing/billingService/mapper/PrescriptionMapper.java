package com.example.billing.billingService.mapper;

import com.example.billing.billingService.Model.PrescriptionItem;
import com.example.billing.billingService.dto.PrescriptionRequest;

import java.util.List;
import java.util.stream.Collectors;

public class PrescriptionMapper {
    public static List<PrescriptionItem> presReqToPresItems(List<PrescriptionRequest> prescriptionRequest) {

        List<PrescriptionItem> responseItems = prescriptionRequest.stream().map(P->{

                    PrescriptionItem prescriptionItem = new PrescriptionItem();

                    prescriptionItem.setType(P.getType());
                    prescriptionItem.setItemId(P.getItemId());
                    prescriptionItem.setName(P.getMedicineName());
                    prescriptionItem.setQuantity(P.getQuantity());
                    prescriptionItem.setDosage(P.getDosage());
                    return prescriptionItem;
                }
        ).collect(Collectors.toList());

        return responseItems;
    }
}
