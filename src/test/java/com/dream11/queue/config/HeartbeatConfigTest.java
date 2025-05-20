package com.dream11.queue.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HeartbeatConfigTest {

  @Test
  void testDefaultValues() {
    // Arrange
    HeartbeatConfig heartbeatConfig = new HeartbeatConfig();

    // Act and Assert
    assertThat(heartbeatConfig.getHeartbeatInterval()).isEqualTo(-1);
    assertThat(heartbeatConfig.getExecutorThreadPoolSize()).isEqualTo(2);
  }

  @Test
  void testDefaultValuesBuilder() {
    // Arrange
    HeartbeatConfig heartbeatConfig = HeartbeatConfig.builder().build();

    // Act and Assert
    assertThat(heartbeatConfig.getHeartbeatInterval()).isEqualTo(-1);
    assertThat(heartbeatConfig.getExecutorThreadPoolSize()).isEqualTo(2);
  }

  @Test
  void testCustomValues() {
    // Arrange
    int customInterval = 30;
    int customPoolSize = 5;
    HeartbeatConfig heartbeatConfig =
        HeartbeatConfig.builder()
            .heartbeatInterval(customInterval)
            .executorThreadPoolSize(customPoolSize)
            .build();

    // Act and Assert
    assertThat(heartbeatConfig.getHeartbeatInterval()).isEqualTo(customInterval);
    assertThat(heartbeatConfig.getExecutorThreadPoolSize()).isEqualTo(customPoolSize);
  }
}
