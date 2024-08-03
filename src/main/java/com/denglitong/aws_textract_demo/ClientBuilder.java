/**
 * Copyright (C) 2022 Urban Compass, Inc.
 */
package com.denglitong.aws_textract_demo;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.augmentedairuntime.AmazonAugmentedAIRuntime;
import com.amazonaws.services.augmentedairuntime.AmazonAugmentedAIRuntimeClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sagemaker.AmazonSageMaker;
import com.amazonaws.services.sagemaker.AmazonSageMakerClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;

import java.io.IOException;
import java.io.InputStream;

public class ClientBuilder {

  private static final String REGION = "us-east-1";
  private static final String HUMAN_TASK_UI_TEMPLATE = "aws_textract/humanTaskUiTemplate.html";

  public static AmazonTextract createTextractClient() {
    return AmazonTextractClientBuilder.standard()
        .withRegion(REGION)
        .build();
  }

  public static AmazonS3 createS3Client() {
    return AmazonS3ClientBuilder.standard()
        .withRegion(REGION)
        .build();
  }

  public static AmazonSageMaker createSageMakerClient() {
    return AmazonSageMakerClientBuilder.standard()
        .withRegion(REGION)
        .build();
  }

  public static AmazonAugmentedAIRuntime createA2IClient() {
    return AmazonAugmentedAIRuntimeClient.builder()
        .build();
  }

  public static String getHumanTaskUiContent() throws IOException {
    InputStream in = ClientBuilder.class.getClassLoader().getResourceAsStream(HUMAN_TASK_UI_TEMPLATE);
    return new String(in.readAllBytes());
  }

  public static AmazonSNS createSNSClient() {
    return AmazonSNSClientBuilder.standard()
            .withRegion(REGION)
            .withCredentials(new DefaultAWSCredentialsProviderChain())
            .build();
  }

  public static AmazonSQS createSQSClient() {
    return AmazonSQSClientBuilder.standard()
            .withRegion(REGION)
            .withCredentials(new DefaultAWSCredentialsProviderChain())
            .build();
  }
}
