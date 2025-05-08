package com.dream11.queue.impl.sqs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dream11.queue.QueueProvider;
import com.dream11.queue.config.HeartbeatConfig;
import org.junit.jupiter.api.Test;

class SqsConfigTest {
  @Test
  void testSqsConfigProvider() {
    // Arrange
    SqsConfig sqsConfig =
        SqsConfig.builder()
            .queueUrl("requestQueue")
            .region("us-east-1")
            .heartbeatConfig(
                HeartbeatConfig.builder().executorThreadPoolSize(5).heartbeatInterval(5).build())
            .build();

    // Act and Assert
    assertThat(sqsConfig.getProvider()).isEqualTo(QueueProvider.SQS);
    assertThat(sqsConfig.getHeartbeatConfig().getHeartbeatInterval()).isEqualTo(5);
    assertThat(sqsConfig.getHeartbeatConfig().getExecutorThreadPoolSize()).isEqualTo(5);
  }

  @Test
  void testSqsConfigProviderNoQueueUrl() {
    SqsConfig.SqsConfigBuilder builder = SqsConfig.builder().region("us-east-1");
    // Act and Assert
    assertThatThrownBy(builder::build)
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("queueUrl is marked non-null but is null");
  }

  @Test
  void testSqsConfigProviderNoRegion() {
    SqsConfig.SqsConfigBuilder builder = SqsConfig.builder().queueUrl("queue");
    // Act and Assert
    assertThatThrownBy(builder::build)
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("region is marked non-null but is null");
  }
}
