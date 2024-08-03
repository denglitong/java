package com.denglitong.aws_lambda_demo.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class HelloHandler implements RequestHandler<Map<String, String>, String> {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String handleRequest(Map<String, String> event, Context context) {
        LambdaLogger logger = context.getLogger();

        logger.log("event: " + gson.toJson(event));

        String userName = (event != null && event.get("name") != null)
                ? event.get("name")
                : "china.compass";

        return String.format("Hello, %s !", userName);
    }
}
