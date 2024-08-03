package com.denglitong.dynamodb_client_demo.builder;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import static com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import static com.denglitong.dynamodb_client_demo.config.DynamoDBConfig.*;

public class DynamoDBBuilder {

    public static DynamoDB getInstance() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new EndpointConfiguration(ENDPOINT, REGION))
                .build();

        return new DynamoDB(client);
    }
}
