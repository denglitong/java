package com.denglitong.grpc_demo.grpc;

import com.denglitong.grpc_demo.gencode.helloworld.HelloRequest;
import com.denglitong.grpc_demo.gencode.helloworld.HelloResponse;
import com.denglitong.grpc_demo.gencode.helloworld.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import static java.util.concurrent.TimeUnit.SECONDS;

public class HelloClient {

    public static void main(String[] args) throws InterruptedException {
        int port = 5000;

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port)
                .usePlaintext()
                .build();

        HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);

        HelloRequest request = HelloRequest.newBuilder()
                .setFirstName("Litong")
                .setLastName("Deng")
                .build();

        HelloResponse helloResponse = stub.hello(request);
        System.out.println(helloResponse.getGreeting());

        channel.shutdown().awaitTermination(5, SECONDS);
    }
}
