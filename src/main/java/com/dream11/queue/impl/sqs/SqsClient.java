package com.dream11.queue.impl.sqs;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.SqsAsyncClientBuilder;
import software.amazon.awssdk.services.sqs.model.ChangeMessageVisibilityRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

/**
 * Client for interacting with Amazon SQS. This class handles the low-level operations of sending,
 * receiving, and managing messages in an SQS queue.
 */
public class SqsClient {
  private final SqsConfig sqsConfig;
  private final SqsAsyncClient sqsAsyncClient;

  /**
   * Constructs a new SqsClient with the given configuration and SQS client. This constructor is
   * useful when you want to provide a custom SQS client.
   *
   * @param sqsConfig The SQS configuration.
   * @param sqsAsyncClient The SQS async client.
   */
  public SqsClient(SqsConfig sqsConfig, SqsAsyncClient sqsAsyncClient) {
    this.sqsConfig = sqsConfig;
    this.sqsAsyncClient = sqsAsyncClient;
  }

  /**
   * Constructs a new SqsClient with the given configuration. Creates a new SQS client using the
   * default credentials provider and the specified region. If an endpoint is provided in the
   * configuration, it will be used instead of the default endpoint.
   *
   * @param sqsConfig The SQS configuration.
   */
  public SqsClient(SqsConfig sqsConfig) {
    this.sqsConfig = sqsConfig;
    SqsAsyncClientBuilder sqsClientBuilder =
        SqsAsyncClient.builder()
            .credentialsProvider(DefaultCredentialsProvider.create())
            .region(Region.of(sqsConfig.getRegion()));
    if (this.sqsConfig.getEndpoint() != null && !this.sqsConfig.getEndpoint().isEmpty()) {
      sqsClientBuilder.endpointOverride(URI.create(sqsConfig.getEndpoint()));
    }
    this.sqsAsyncClient = sqsClientBuilder.build();
  }

  /**
   * Receives a list of messages asynchronously from the SQS queue. The number of messages received
   * is determined by the configuration.
   *
   * @return A CompletableFuture containing a list of received messages.
   */
  public CompletableFuture<List<Message>> receive() {
    return this.sqsAsyncClient
        .receiveMessage(
            ReceiveMessageRequest.builder()
                .queueUrl(this.sqsConfig.getQueueUrl())
                .maxNumberOfMessages(this.sqsConfig.getReceiveConfig().getMaxMessages())
                .build())
        .thenApply(ReceiveMessageResponse::messages);
  }

  /**
   * Receives a list of messages asynchronously with a specified timeout. The number of messages
   * received is determined by the configuration.
   *
   * @param timeout The timeout in seconds to wait for messages.
   * @return A CompletableFuture containing a list of received messages.
   */
  public CompletableFuture<List<Message>> receive(int timeout) {
    return this.sqsAsyncClient
        .receiveMessage(
            ReceiveMessageRequest.builder()
                .queueUrl(this.sqsConfig.getQueueUrl())
                .waitTimeSeconds(timeout)
                .maxNumberOfMessages(this.sqsConfig.getReceiveConfig().getMaxMessages())
                .build())
        .thenApply(ReceiveMessageResponse::messages);
  }

  /**
   * Deletes a message from the SQS queue. This is typically called after a message has been
   * successfully processed.
   *
   * @param message The message to delete.
   * @return A CompletableFuture that completes when the message is deleted.
   */
  public CompletableFuture<Void> deleteMessage(Message message) {
    return this.sqsAsyncClient
        .deleteMessage(
            DeleteMessageRequest.builder()
                .queueUrl(sqsConfig.getQueueUrl())
                .receiptHandle(message.receiptHandle())
                .build())
        .thenAccept(__ -> {});
  }

  /**
   * Sends a message asynchronously to the SQS queue.
   *
   * @param message The message to send.
   * @return A CompletableFuture that completes when the message is sent.
   */
  public CompletableFuture<Void> send(String message) {
    return this.sqsAsyncClient
        .sendMessage(
            SendMessageRequest.builder()
                .queueUrl(sqsConfig.getQueueUrl())
                .messageBody(message)
                .build())
        .thenAccept(__ -> {});
  }

  /**
   * Changes the visibility timeout of a message. This is useful for extending the time a message is
   * invisible to other consumers.
   *
   * @param message The message to change visibility for.
   * @param visibilityTimeout The new visibility timeout in seconds.
   * @return A CompletableFuture that completes when the message visibility is changed.
   */
  public CompletableFuture<Void> changeMessageVisibility(Message message, int visibilityTimeout) {
    return this.sqsAsyncClient
        .changeMessageVisibility(
            ChangeMessageVisibilityRequest.builder()
                .queueUrl(this.sqsConfig.getQueueUrl())
                .receiptHandle(message.receiptHandle())
                .visibilityTimeout(visibilityTimeout)
                .build())
        .thenAccept(__ -> {});
  }

  /** Closes the SQS client, releasing any resources. */
  public void close() {
    this.sqsAsyncClient.close();
  }
}
