/**
 * Copyright (C) 2022 Urban Compass, Inc.
 */
package com.denglitong.aws_textract_demo;

import com.amazonaws.services.sagemaker.AmazonSageMaker;
import com.amazonaws.services.sagemaker.model.CreateFlowDefinitionRequest;
import com.amazonaws.services.sagemaker.model.CreateFlowDefinitionResult;
import com.amazonaws.services.sagemaker.model.DeleteFlowDefinitionRequest;
import com.amazonaws.services.sagemaker.model.DeleteFlowDefinitionResult;
import com.amazonaws.services.sagemaker.model.FlowDefinitionOutputConfig;
import com.amazonaws.services.sagemaker.model.HumanLoopConfig;

import static com.denglitong.aws_textract_demo.AwsTextractApp.AWS_ACCOUNT_ID;

public class CreateWorkflow {
  // gamma, prod
  public static String REGION = "gamma";

  private static final AmazonSageMaker SAGE_MAKER_CLIENT = ClientBuilder.createSageMakerClient();
  public static final String HUMAN_REVIEW_TEAM_ARN =
      String.format("arn:aws:sagemaker:us-east-1:%s:workteam/private-crowd/document-splitting-hitl-%s", AWS_ACCOUNT_ID, REGION);
  public static final String HUMAN_TASK_UI_ARN =
      String.format("arn:aws:sagemaker:us-east-1:%s:human-task-ui/document-splitting-hitl-dip-%s", AWS_ACCOUNT_ID, REGION);
  public static final String HUMAN_FLOW_OUTPUT_PATH = "s3://" + REGION + "-doc-split-hitl/human_review_output/";
  public static final String HUMAN_REVIEW_ROLE_ARN =
      String.format("arn:aws:iam::%s:role/doc_split_hitl_a2i_%s", AWS_ACCOUNT_ID, REGION);

  public static void deleteWorkflow(String workflowName) throws InterruptedException {
    DeleteFlowDefinitionResult result = SAGE_MAKER_CLIENT.deleteFlowDefinition(
        new DeleteFlowDefinitionRequest().withFlowDefinitionName(workflowName));
    System.out.println("deleteFlowDefinitionResult: " + result);
    Thread.sleep(5000);
  }

  public static void createWorkflow(String workflowName, String taskTitle, String taskDescription) {
    HumanLoopConfig humanLoopConfig = new HumanLoopConfig()
        .withWorkteamArn(HUMAN_REVIEW_TEAM_ARN) // specify the human review team
        .withHumanTaskUiArn(HUMAN_TASK_UI_ARN) // specify the UI template
        .withTaskCount(1)
        .withTaskTitle(taskTitle)
        .withTaskDescription(taskDescription);

    CreateFlowDefinitionRequest request = new CreateFlowDefinitionRequest()
        .withFlowDefinitionName(workflowName)
        .withHumanLoopConfig(humanLoopConfig)
        .withOutputConfig(
            new FlowDefinitionOutputConfig().withS3OutputPath(HUMAN_FLOW_OUTPUT_PATH))
        .withRoleArn(HUMAN_REVIEW_ROLE_ARN);

    CreateFlowDefinitionResult result = SAGE_MAKER_CLIENT.createFlowDefinition(request);
    System.out.println("createFlowDefinitionResponse: " + result);
  }

  public static void main(String[] args) throws InterruptedException {
    REGION = "prod";
    String workflowName = "document-splitting-hitl-" + REGION;
    deleteWorkflow(workflowName);
    createWorkflow(
        workflowName,
        "Document Splitting HITL Review Task - [" + REGION + "]",
        "Doc Splitting related review tasks - [" + REGION + "]"
    );
  }
}
