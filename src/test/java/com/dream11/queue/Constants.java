package com.dream11.queue;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
  public static String LOCALSTACK_DOCKER_IMAGE = "localstack/localstack:1.3.0";
  public static String SQS_QUEUE = "test";
  public static String SQS_ENDPOINT = "SQS_ENDPOINT";
  public static String AWS_REGION = "aws.region";
  public static String AWS_ACCESS_KEY_ID = "aws.accessKeyId";
  public static String AWS_SECRET_ACCESS_KEY = "aws.secretAccessKey";
}
