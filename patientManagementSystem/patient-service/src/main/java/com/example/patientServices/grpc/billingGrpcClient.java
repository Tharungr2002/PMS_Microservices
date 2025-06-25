package com.example.patientServices.grpc;

import com.example.billing.grpc.BillingRequest;
import com.example.billing.grpc.BillingResponse;
import com.example.billing.grpc.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class billingGrpcClient {

    private BillingServiceGrpc.BillingServiceBlockingStub blockingstub;

    public billingGrpcClient(@Value("${billing.service.address:localhost}") String serverAddress,
                             @Value("${billing.service.grpc.port:9001}") int serverPort) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,serverPort).usePlaintext().build();

        blockingstub = BillingServiceGrpc.newBlockingStub(channel);

    }

    public BillingResponse createBillingAccount(String patientId, String name) {

        BillingRequest request = BillingRequest.newBuilder().setPatientId(patientId).setName(name).build();

        BillingResponse response = blockingstub.createBillingAccount(request);
        return response;
    }
}
