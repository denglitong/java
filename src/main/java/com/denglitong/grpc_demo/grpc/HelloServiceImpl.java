package com.denglitong.grpc_demo.grpc;

import com.denglitong.grpc_demo.gencode.helloworld.HelloRequest;
import com.denglitong.grpc_demo.gencode.helloworld.HelloResponse;
import com.denglitong.grpc_demo.gencode.helloworld.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String greeting = new StringBuilder()
                .append("Hello, ")
                .append(request.getFirstName())
                .append(" ")
                .append(request.getLastName())
                .append(" ")
                .append(request.getEmail())
                .toString();

        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
