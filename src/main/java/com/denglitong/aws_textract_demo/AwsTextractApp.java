/**
 * Copyright (C) 2022 Urban Compass, Inc.
 */
package com.denglitong.aws_textract_demo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.amazonaws.services.augmentedairuntime.AmazonAugmentedAIRuntime;
import com.amazonaws.services.augmentedairuntime.model.DescribeHumanLoopRequest;
import com.amazonaws.services.augmentedairuntime.model.DescribeHumanLoopResult;
import com.amazonaws.services.augmentedairuntime.model.HumanLoopInput;
import com.amazonaws.services.augmentedairuntime.model.StartHumanLoopRequest;
import com.amazonaws.services.augmentedairuntime.model.StartHumanLoopResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.sagemaker.AmazonSageMaker;
import com.amazonaws.services.sagemaker.model.CreateFlowDefinitionRequest;
import com.amazonaws.services.sagemaker.model.CreateFlowDefinitionResult;
import com.amazonaws.services.sagemaker.model.CreateHumanTaskUiRequest;
import com.amazonaws.services.sagemaker.model.CreateHumanTaskUiResult;
import com.amazonaws.services.sagemaker.model.DeleteHumanTaskUiRequest;
import com.amazonaws.services.sagemaker.model.DeleteHumanTaskUiResult;
import com.amazonaws.services.sagemaker.model.FlowDefinitionOutputConfig;
import com.amazonaws.services.sagemaker.model.HumanLoopConfig;
import com.amazonaws.services.sagemaker.model.UiTemplate;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.BoundingBox;
import com.amazonaws.services.textract.model.DocumentLocation;
import com.amazonaws.services.textract.model.FeatureType;
import com.amazonaws.services.textract.model.Geometry;
import com.amazonaws.services.textract.model.GetDocumentAnalysisRequest;
import com.amazonaws.services.textract.model.GetDocumentAnalysisResult;
import com.amazonaws.services.textract.model.NotificationChannel;
import com.amazonaws.services.textract.model.OutputConfig;
import com.amazonaws.services.textract.model.Point;
import com.amazonaws.services.textract.model.Relationship;
import com.amazonaws.services.textract.model.S3Object;
import com.amazonaws.services.textract.model.StartDocumentAnalysisRequest;
import com.amazonaws.services.textract.model.StartDocumentAnalysisResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

public class AwsTextractApp {
  private static final Gson GSON = new GsonBuilder().create();

  private static final String BUCKET = System.getProperty("bucket", "pre-compliance-test");

  private static final String DOCUMENT = System.getProperty("document",
      "test_samples/CCPA-Consumer_Privacy_Act_Advisory_SD.pdf");

  public static final String AWS_ACCOUNT_ID = "YOUR_ACCOUNT_ID";

  private static final String DOCUMENT_INTELLIGENCE_ACCOUNT_ROLE_ARN
      = String.format("arn:aws:iam::%s:role/DocumentIntelligence_Account_Access_Role", AWS_ACCOUNT_ID);

  private static final String START_DOCUMENT_ANALYSIS_SNS_TOPIC_ARN
      = String.format("arn:aws:sns:us-east-1:%s:AmazonTextract-start-document-analysis-topic-dev", AWS_ACCOUNT_ID);

  private static final String START_DOCUMENT_ANALYSIS_SQS_QUEUE_URL
      = String.format("https://sqs.us-east-1.amazonaws.com/%s/start-document-analysis-queue-dev", AWS_ACCOUNT_ID);

  private static final String HUMAN_REVIEW_WORKFLOW_ARN
      = String.format("arn:aws:sagemaker:us-east-1:%s:flow-definition/gamma-compliance-flow-default", AWS_ACCOUNT_ID);

  private static final String HUMAN_TASK_UI_ARN
      = String.format("arn:aws:sagemaker:us-east-1:%s:human-task-ui/gamma-compliance-ui-template-default", AWS_ACCOUNT_ID);

  private static final String HUMAN_REVIEW_TEAM_ARN
      = String.format("arn:aws:sagemaker:us-east-1:%s:workteam/private-crowd/compliance", AWS_ACCOUNT_ID);

  private static final String HUMAN_FLOW_OUTPUT_PATH
      = "s3://gamma-compliance-document/human-loop-result-output";

  private static final AmazonTextract TEXTRACT_CLIENT = ClientBuilder.createTextractClient();
  private static final AmazonSageMaker SAGE_MAKER_CLIENT = ClientBuilder.createSageMakerClient();
  private static final AmazonAugmentedAIRuntime A2I_CLIENT = ClientBuilder.createA2IClient();
  private static final AmazonS3 S3_CLIENT = ClientBuilder.createS3Client();
  private static final AmazonSQS SQS_CLIENT = ClientBuilder.createSQSClient();
  private static final AmazonSNS SNS_CLIENT = ClientBuilder.createSNSClient();

  private static final String KEY_VALUE_SET = "KEY_VALUE_SET";
  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";
  private static final String CHILD = "CHILD";
  private static final String WORD = "WORD";
  private static final String PRINTED = "PRINTED";
  private static long CURRENT_TIMESTAMP = Instant.now().toEpochMilli();

  private StartDocumentAnalysisResult startDocumentAnalysisResult;
  private GetDocumentAnalysisResult getDocumentAnalysisResult;
  private CreateFlowDefinitionResult createFlowDefinitionResult;
  private CreateHumanTaskUiResult createHumanTaskUiResult;
  private StartHumanLoopResult startHumanLoopResult;

  public void startAsyncAnalysisDocument(String bucket, String document) {
    S3Object s3Object = new S3Object().withBucket(bucket).withName(document);

    NotificationChannel notificationChannel = new NotificationChannel()
        .withRoleArn(DOCUMENT_INTELLIGENCE_ACCOUNT_ROLE_ARN)
        .withSNSTopicArn(START_DOCUMENT_ANALYSIS_SNS_TOPIC_ARN);

    StartDocumentAnalysisRequest request = new StartDocumentAnalysisRequest()
        .withFeatureTypes(FeatureType.FORMS)
        .withDocumentLocation(new DocumentLocation().withS3Object(s3Object))
        .withOutputConfig(new OutputConfig().withS3Bucket(bucket).withS3Prefix("textract_output"))
        .withNotificationChannel(notificationChannel);

    startDocumentAnalysisResult = TEXTRACT_CLIENT.startDocumentAnalysis(request);
    System.out.println("startDocumentAnalysisResult: " + startDocumentAnalysisResult);
  }

  public void fetchAsyncAnalysisDocumentResult(String jobId) {
    GetDocumentAnalysisRequest request = new GetDocumentAnalysisRequest().withJobId(jobId);
    getDocumentAnalysisResult = TEXTRACT_CLIENT.getDocumentAnalysis(request);

    while ("IN_PROGRESS".equals(getDocumentAnalysisResult.getJobStatus())) {
      try {
        Thread.sleep(1000);
        System.out.println("getJobStatus: " + getDocumentAnalysisResult.getJobStatus());
        getDocumentAnalysisResult = TEXTRACT_CLIENT.getDocumentAnalysis(request);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    GetDocumentAnalysisResult paginationResult = getDocumentAnalysisResult;
    while (StringUtils.isNotBlank(paginationResult.getNextToken())) {
      request.setNextToken(paginationResult.getNextToken());
      paginationResult = TEXTRACT_CLIENT.getDocumentAnalysis(request);
      getDocumentAnalysisResult.getBlocks().addAll(paginationResult.getBlocks());
    }

    System.out.println(
        "getDocumentAnalysisResult blocks size:" + getDocumentAnalysisResult.getBlocks().size());
  }

  public void createHumanTaskUi(String taskUiName) throws IOException {
    CreateHumanTaskUiRequest humanTaskUiRequest = new CreateHumanTaskUiRequest()
        .withHumanTaskUiName(taskUiName)
        .withUiTemplate(new UiTemplate()
            .withContent(ClientBuilder.getHumanTaskUiContent()));

    createHumanTaskUiResult = SAGE_MAKER_CLIENT.createHumanTaskUi(humanTaskUiRequest);

    System.out.println("createHumanTaskUiResult:" + createHumanTaskUiResult);
  }

  public void createHumanReviewFlow(String workflowName, String taskTitle) {
    HumanLoopConfig humanLoopConfig = new HumanLoopConfig()
        .withWorkteamArn(HUMAN_REVIEW_TEAM_ARN) // specify the human review team
        .withHumanTaskUiArn(createHumanTaskUiResult.getHumanTaskUiArn()) // specify the UI template
        .withTaskCount(1)
        .withTaskTitle(taskTitle)
        .withTaskDescription("Perform the task by following the instructions");

    CreateFlowDefinitionRequest flowDefinitionRequest = new CreateFlowDefinitionRequest()
        .withFlowDefinitionName(workflowName)
        .withHumanLoopConfig(humanLoopConfig)
        .withOutputConfig(
            new FlowDefinitionOutputConfig().withS3OutputPath(HUMAN_FLOW_OUTPUT_PATH))
        .withRoleArn(DOCUMENT_INTELLIGENCE_ACCOUNT_ROLE_ARN);

    createFlowDefinitionResult = SAGE_MAKER_CLIENT.createFlowDefinition(flowDefinitionRequest);
    System.out.println("createFlowDefinitionResponse: " + createFlowDefinitionResult);
  }

  public void startHumanLoopReview(String bucket, String document,
      List<String> detectionKeys) throws IOException {
    String humanLoopName = generateHumanLoopName();
    HumanLoopInput humanLoopInput = buildHumanLoopInput(bucket, document, detectionKeys);

    StartHumanLoopRequest startHumanLoopRequest = new StartHumanLoopRequest()
        .withHumanLoopName(humanLoopName)
        .withHumanLoopInput(humanLoopInput)
        .withFlowDefinitionArn(createFlowDefinitionResult.getFlowDefinitionArn());

    startHumanLoopResult = A2I_CLIENT.startHumanLoop(startHumanLoopRequest);
    System.out.println("startHumanLoopResult: " + startHumanLoopResult);

    for (int i = 0; i < 3; i++) {
      DescribeHumanLoopResult result = A2I_CLIENT.describeHumanLoop(new DescribeHumanLoopRequest()
          .withHumanLoopName(humanLoopName));
      System.out.println("describeHumanLoop: " + result);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private HumanLoopInput buildHumanLoopInput(String bucket, String document,
      List<String> detectionKeys) throws IOException {
    fillBlocksEmptyValuesForKey();
    filterKeyValueSetBlocks();

    List<String> keyValuePairBlockIds = filterKVPairBlockIdsByKeys(
        getDocumentAnalysisResult.getBlocks(), detectionKeys);

    HumanLoopInputJson inputJson = new HumanLoopInputJson()
        .withDocumentS3URI(String.format("s3://%s/%s", bucket, document))
        .withBlocks(getSubPageBlocks(null))
        .withKeyValuePairBlockIds(keyValuePairBlockIds)
        .withImportantFormKeys(
            detectionKeys.stream()
                .map(key -> new ImportantFormKey(key, new ArrayList<>()))
                .collect(Collectors.toList()));

    String inputJsonFilePath = document + ".json";
    uploadHumanLoopInputToS3(inputJsonFilePath, inputJson);

    JsonObject inputContent = new JsonObject();
    inputContent.addProperty("documentS3Uri",
        String.format("s3://%s/%s", bucket, document));
    inputContent.addProperty("inputJsonS3Uri",
        String.format("s3://%s/%s", bucket, inputJsonFilePath));

    System.out.println(inputContent.toString());
    return new HumanLoopInput().withInputContent(inputContent.toString());
  }

  private void uploadHumanLoopInputToS3(String filePath, HumanLoopInputJson inputJson)
      throws IOException {
    byte[] content = GSON.toJson(inputJson).getBytes(StandardCharsets.UTF_8);
    InputStream is = new ByteArrayInputStream(content);
    ObjectMetadata meta = new ObjectMetadata();
    meta.setContentLength(content.length);

    PutObjectRequest request = new PutObjectRequest(BUCKET, filePath, is,
        meta);
    S3_CLIENT.putObject(request);
    is.close();
  }

  private List<Block> getSubPageBlocks(Integer pageNum) {
    if (pageNum == null) {
      return getDocumentAnalysisResult.getBlocks();
    }
    return getDocumentAnalysisResult.getBlocks()
        .stream()
        .filter(block -> block.getPage().equals(pageNum))
        .collect(Collectors.toList());
  }

  private void fillBlocksEmptyValuesForKey() {
    Block emptyBlock = new Block().withId(UUID.randomUUID().toString())
        .withBlockType("WORD")
        .withConfidence(0f)
        .withText("null")
        .withTextType("PRINTED")
        .withGeometry(new Geometry()
            .withBoundingBox(
                new BoundingBox().withLeft(0f).withTop(0f).withWidth(0f).withHeight(0f))
            .withPolygon(new Point().withX(0f).withY(0f))
        );

    for (Block block : getDocumentAnalysisResult.getBlocks()) {
      if (!("KEY_VALUE_SET".equals(block.getBlockType()) &&
          block.getEntityTypes().contains("KEY"))) {
        return;
      }
      if (block.getRelationships().stream()
          .noneMatch(relationship -> "VALUE".equals(relationship.getType()))) {
        block.getRelationships().add(new Relationship().withType("VALUE")
            .withIds(Arrays.asList(emptyBlock.getId())));
      }
      if (block.getRelationships().stream()
          .noneMatch(relationship -> "CHILD".equals(relationship.getType()))) {
        block.getRelationships()
            .add(new Relationship().withType("CHILD")
                .withIds(Arrays.asList(emptyBlock.getId())));
      }
    }
    getDocumentAnalysisResult.getBlocks().add(emptyBlock);
  }

  private void filterKeyValueSetBlocks() {
    Map<String, Block> blockMap = new HashMap<>();
    List<String> keyValueSetIds = new ArrayList<>();

    for (Block block : getDocumentAnalysisResult.getBlocks()) {
      blockMap.put(block.getId(), block);
      if ("KEY_VALUE_SET".equals(block.getBlockType())) {
        keyValueSetIds.add(block.getId());
      }
    }

    Set<String> blockIdFiltered = new HashSet<>(keyValueSetIds);
    for (String keyValueSetId : keyValueSetIds) {
      blockIdFiltered.addAll(
          Optional.ofNullable(blockMap.get(keyValueSetId).getRelationships())
              .orElse(new ArrayList<>())
              .stream()
              .flatMap(relationship -> relationship.getIds().stream())
              .collect(Collectors.toList()));
    }

    getDocumentAnalysisResult.setBlocks(
        blockMap.entrySet()
            .stream()
            .filter(entry -> blockIdFiltered.contains(entry.getKey()))
            .map(Map.Entry::getValue)
            .collect(Collectors.toList()));

    System.out.println(
        "after filter KEY_VALUE_SET blocks size: " + getDocumentAnalysisResult.getBlocks().size());
  }

  private static List<String> filterKVPairBlockIdsByKeys(List<Block> blocks,
      List<String> keys) {
    Map<String, Block> blockMap = blocks.stream()
        .collect(Collectors.toMap(Block::getId, Function.identity()));

    return blocks.stream()
        .filter(block -> {
              if (!(KEY_VALUE_SET.equals(block.getBlockType()) &&
                  block.getEntityTypes().contains(KEY))) {
                return false;
              }
              String key = Optional.ofNullable(block.getRelationships())
                  .orElse(new ArrayList<>())
                  .stream()
                  .filter(relationship -> CHILD.equals(relationship.getType()))
                  .flatMap(relationship -> relationship.getIds().stream())
                  .map(id -> blockMap.get(id).getText())
                  .collect(Collectors.joining(" "));
              // we can expand the match pattern here
              return keys.contains(key);
            }
        )
        .map(Block::getId)
        .collect(Collectors.toList());
  }

  private static class HumanLoopInputJson {
    private String documentS3URI;
    private List<Block> blocks;
    private List<String> keyValuePairBlockIds;
    private List<ImportantFormKey> importantFormKeys;

    public HumanLoopInputJson() {
    }

    public HumanLoopInputJson withDocumentS3URI(String documentS3URI) {
      this.documentS3URI = documentS3URI;
      return this;
    }

    public HumanLoopInputJson withBlocks(List<Block> blocks) {
      this.blocks = blocks;
      return this;
    }

    public HumanLoopInputJson withKeyValuePairBlockIds(List<String> keyValuePairBlockIds) {
      this.keyValuePairBlockIds = keyValuePairBlockIds;
      return this;
    }

    public HumanLoopInputJson withImportantFormKeys(List<ImportantFormKey> importantFormKeys) {
      this.importantFormKeys = importantFormKeys;
      return this;
    }
  }

  public static class ImportantFormKey {
    private String importantFormKey;
    private List<String> importantFormKeyAliases;

    public ImportantFormKey(String importantFormKey, List<String> importantFormKeyAliases) {
      this.importantFormKey = importantFormKey;
      this.importantFormKeyAliases = importantFormKeyAliases;
    }
  }

  private String generateFlowDefinitionName() {
    return String.format("human-flow-%d", CURRENT_TIMESTAMP);
  }

  private String generateHumanTaskUiName() {
    return String.format("human-task-%d", CURRENT_TIMESTAMP);
  }

  private String generateHumanLoopName() {
    return String.format("human-loop-%d", CURRENT_TIMESTAMP);
  }

  private static final Map<String, DocumentArg> documentTypesMap
      = new HashMap<>();

  {
    // documentTypesMap.put("AD-Agency Relationship (Seller)",
    //     new DocumentArg(null, null,
    //         Arrays.asList("Buyer/Seller/Landlord/Tenant", "Date"))
    // );
    //
    // documentTypesMap.put(
    //     "Discl Regard Real Estate Agency Relationship",
    //     new DocumentArg("pre-compliance-test",
    //         "test_samples/Discl_Regard_Real_Estate_Agency_Relationship.pdf",
    //         Arrays.asList("Buyer/Seller/Landlord/Tenant", "Date"))
    // );

    // documentTypesMap.put("CCPA-Consumer Privacy Act Advisory",
    //     new DocumentArg("pre-compliance-test",
    //         "test_samples/CCPA-Consumer_Privacy_Act_Advisory_SD.pdf",
    //         Arrays.asList("Buyer/Seller/Landlord/Tenant", "Date"))
    // );

    // documentTypesMap.put("Compass Affiliated Business Arrangement Disclosure",
    //     new DocumentArg("pre-compliance-test",
    //         "test_samples/Compass_Affiliated_Business_Arrangement_Disclosure_DD.pdf",
    //         Arrays.asList("Buyer's Signature", "Seller's Signature", "Date"))
    // );

    // documentTypesMap.put("Compass Seller or Tenant Ack. (Secure Belongings)",
    //     new DocumentArg("pre-compliance-test",
    //         "test_samples/Seller_or_Tenant_Acknowledgment_to_Secure_and_Protect_Personal_Belongings.pdf",
    //         Arrays.asList("Seller/Tenant", "Date"))
    // );
    //
    // documentTypesMap.put("FHDA-Fair Housing & Discrimination Advisory",
    //     new DocumentArg("pre-compliance-test",
    //         "test_samples2/FHDA-Fair_Housing__Discrimination_Advisory.pdf",
    //         Arrays.asList("Buyer/Tenant", "Seller/Landlord", "Date"))
    // );
    //
    // documentTypesMap.put("PRBS (L)-Possible Rep. Buyers & Sellers",
    //     new DocumentArg("pre-compliance-test",
    //         "test_samples/PRBS_-_Possible_Representation_More_than_One_BuyerSeller_Tagged.pdf",
    //         Arrays.asList("Seller", "Date"))
    // );
    //
    documentTypesMap.put("WFA (L)-Wire Fraud Advisory (Listing)",
        new DocumentArg("pre-compliance-test",
            "test_samples/WFA_S-Wire_Fraud_Advisory_Sale.pdf",
            Arrays.asList("Buyer/Tenant", "Seller/Landlord", "Date"))
    );
  }

  private static class DocumentArg {
    private String bucket;
    private String key;
    private List<String> detectionKeys;

    public DocumentArg() {
    }

    public DocumentArg(String bucket, String key, List<String> detectionKeys) {
      this.bucket = bucket;
      this.key = key;
      this.detectionKeys = detectionKeys;
    }

    @Override
    public String toString() {
      return "DocumentArg{" +
          "bucket='" + bucket + '\'' +
          ", key='" + key + '\'' +
          ", detectionKeys=" + detectionKeys +
          '}';
    }
  }

  private static void createWorkflowByDocumentTypes() throws IOException {
    AwsTextractApp demo = new AwsTextractApp();

    for (String documentType : documentTypesMap.keySet()) {
      String uiTemplateName = convertDocumentTypeToWorkflowName(documentType);
      String workflowName = uiTemplateName;
      String taskTitle = documentType.substring(0, Math.min(documentType.length(), 128));
      System.out.println(uiTemplateName + " *** " + taskTitle);

      demo.createHumanTaskUi(uiTemplateName);
      // demo.createHumanReviewFlow(workflowName, taskTitle);
    }
  }

  //  pattern for Workflow name / UI template name: [a-z0-9-]{1, 64}
  private static String convertDocumentTypeToWorkflowName(String documentType) {
    String result = documentType.replaceAll("[^a-zA-Z0-9-]+", "-");
    result = result.replaceAll("(^-+)|(-+$)", "");
    result = result.replaceAll("-{2,}", "-");
    result = result.substring(0, Math.min(result.length(), 64)).toLowerCase();
    return result;
  }

  public void receiveAnalysisDocumentResultBySQS() {
    ReceiveMessageRequest request = new ReceiveMessageRequest()
        .withQueueUrl(START_DOCUMENT_ANALYSIS_SQS_QUEUE_URL)
        .withMaxNumberOfMessages(10)
        .withWaitTimeSeconds(10);

    ReceiveMessageResult messageResult = SQS_CLIENT.receiveMessage(request);
    System.out.println("messageResult size:" + messageResult.getMessages().size());

    for (Message message : messageResult.getMessages()) {
      System.out.println("message body:" + message.getBody());
      // Gson gson = new GsonBuilder().create();
      // GetDocumentAnalysisResult documentAnalysisResult = gson.fromJson(message.getBody(),
      //         GetDocumentAnalysisResult.class);
      // TODO
      SQS_CLIENT.deleteMessage(request.getQueueUrl(), message.getReceiptHandle());
    }
  }

  public void sendSNSMessage() {
    PublishRequest publishRequest = new PublishRequest()
        .withTopicArn(START_DOCUMENT_ANALYSIS_SNS_TOPIC_ARN)
        .withMessage("hello, world " + CURRENT_TIMESTAMP);
    System.out.println("publishRequest:" + publishRequest);
    PublishResult publishResult = SNS_CLIENT.publish(publishRequest);
    System.out.println("publishResult:" + publishResult);
  }

  private static void deleteHumanTaskUi() {
    String[] toDeleteNames = new String[] {
        "gamma-ccpa-consumer-privacy-act-advisory",
        "gamma-fhda-fair-housing-discrimination-advisory",
        "gamma-wfa-l-wire-fraud-advisory-listing",
    };
    for (String toDeleteName : toDeleteNames) {
      DeleteHumanTaskUiResult result = SAGE_MAKER_CLIENT.deleteHumanTaskUi(
          new DeleteHumanTaskUiRequest().withHumanTaskUiName(toDeleteName));
      System.out.println(toDeleteName + " : " + result);
    }
  }

  public static void main(String[] args) throws Exception {
    // createWorkflowByDocumentTypes();
    // deleteHumanTaskUi();

    String taskUiName = "litong-20220325-1";

    CreateHumanTaskUiRequest humanTaskUiRequest = new CreateHumanTaskUiRequest()
        .withHumanTaskUiName(taskUiName)
        .withUiTemplate(new UiTemplate()
            .withContent(ClientBuilder.getHumanTaskUiContent()));

    System.out.println(
        "createHumanTaskUiResult:" + SAGE_MAKER_CLIENT.createHumanTaskUi(humanTaskUiRequest));

    // Application demo = new Application();

    // demo.startAsyncAnalysisDocument("pre-compliance-test",
    //     "test_samples/WFA_S-Wire_Fraud_Advisory_Sale.pdf");
    // demo.fetchAsyncAnalysisDocumentResult(demo.startDocumentAnalysisResult.getJobId());
    // Thread.sleep(10000);
    // demo.receiveAnalysisDocumentResultBySQS();

    // for (String documentType : documentTypesMap.keySet()) {
    //   DocumentArg arg = documentTypesMap.get(documentType);
    //   if (StringUtils.isEmpty(arg.bucket) || StringUtils.isEmpty(arg.key)) {
    //     continue;
    //   }
    //
    //   String workflowName = convertDocumentTypeToWorkflowName(documentType);
    //   System.out.println(workflowName);
    //
    //   demo.startAsyncAnalysisDocument(arg.bucket, arg.key);
    //   // you can print the getDocumentAnalysisResult to see the textract result inside here
    //   demo.fetchAsyncAnalysisDocumentResult(demo.startDocumentAnalysisResult.getJobId());
    // }

    // String bucket = "compass-gamma-doc-compliance-input";
    // String[] keys = new String[] {
    //     "CA/6587085/california_consumer_privacy_act_advisory.pdf",
    //     "CA/6594736/compass_affiliated_business_arrangement_disclosure.pdf",
    //     "CA/6590882/fair_housing__discrimin_advisory_-_car_form_fhda.pdf",
    //     "CA/6564601/fair_housing__discrimin_advisory_-_car_form_fhda.pdf",
    //     "CA/6541517/fair_housing__discrimin_advisory_-_car_form_fhda.pdf",
    //     "CA/6504495/fair_housing__discrimin_advisory_-_car_form_fhda.pdf",
    //     "CA/6597442/wfa-wire_fraud_advisory_listing.pdf",
    //     "CA/6597028/sellertenant_ack_of_oblig_to_secure_belongings.pdf",
    //     "CA/6584819/seller_or_tenant_acknowledgement_of_obligation_to_secure___protect_personal_belongings.pdf"
    // };
    // for (String key : keys) {
    //   try {
    //     System.out.println("key: " + key);
    //     demo.startAsyncAnalysisDocument(bucket, key);
    //     // demo.fetchAsyncAnalysisDocumentResult(demo.startDocumentAnalysisResult.getJobId());
    //   } catch (Exception e) {
    //     e.printStackTrace();
    //   }
    // }

    // String humanLoopName = "3957558d58dbce4186cb6c3f1e093fc92560815da2c55a0654366c3d867b3af";
    // System.out.println(A2I_CLIENT.describeHumanLoop(
    //     new DescribeHumanLoopRequest().withHumanLoopName(humanLoopName)));
  }
}