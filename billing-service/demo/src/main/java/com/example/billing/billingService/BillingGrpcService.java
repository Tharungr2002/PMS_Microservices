package com.example.billing.billingService;

import com.example.billing.grpc.BillingRequest;
import com.example.billing.grpc.BillingResponse;
import com.example.billing.grpc.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    @Override
    public void createBillingAccount(BillingRequest request, StreamObserver<BillingResponse> responseObserver) {
        System.out.println(request.toString());

        BillingResponse response = BillingResponse.newBuilder().setAccountId("55").setStatus("ACTIVE").build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
