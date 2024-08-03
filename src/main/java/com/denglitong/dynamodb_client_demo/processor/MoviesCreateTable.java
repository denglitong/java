package com.denglitong.dynamodb_client_demo.processor;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.denglitong.dynamodb_client_demo.builder.DynamoDBBuilder;

import static com.denglitong.dynamodb_client_demo.config.DynamoDBConfig.*;
import static java.util.Arrays.asList;

public class MoviesCreateTable {

    public static void main(String[] args) throws InterruptedException {
        DynamoDB dynamoDB = DynamoDBBuilder.getInstance();

        System.out.println("Attempting to create table, please wait...");

        Table table = dynamoDB.getTable(MOVIES);
        if (table == null) {
            table = dynamoDB.createTable(MOVIES,
                    // key definition
                    asList(
                            new KeySchemaElement(YEAR, KeyType.HASH),
                            new KeySchemaElement(TITLE, KeyType.RANGE)
                    ),
                    // attributes definition
                    asList(
                            new AttributeDefinition(YEAR, ScalarAttributeType.N),
                            new AttributeDefinition(TITLE, ScalarAttributeType.S)
                    ),
                    // read-write capacity config
                    new ProvisionedThroughput(10L, 10L)
            );
        }

        table.waitForActive();

        System.out.println("Success. Table status: " + table.getDescription());
    }
}
