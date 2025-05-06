package com.dream11.queue.impl.sqs;

import com.dream11.queue.QueueProvider;
import com.dream11.queue.config.HeartbeatConfig;
import com.dream11.queue.config.QueueConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SqsConfig implements QueueConfig {
  /** The URL of the SQS queue. */
  @NonNull private String queueUrl;
  /** The AWS region where the SQS queue is located. */
  @NonNull private String region;
  /** The endpoint for the SQS queue. */
  private String endpoint;

  /** The configuration for receiving messages from the SQS queue. */
  @Builder.Default private ReceiveConfig receiveConfig = new ReceiveConfig();

  /** The configuration for heartbeat settings. */
  @Builder.Default private HeartbeatConfig heartbeatConfig = new HeartbeatConfig();

  /**
   * Returns the provider type for this configuration.
   *
   * @return The QueueProvider type.
   */
  @Override
  public QueueProvider getProvider() {
    return QueueProvider.SQS;
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ReceiveConfig {
    /** The maximum number of messages to receive from the SQS queue. */
    private int maxMessages = 1;
  }
}
