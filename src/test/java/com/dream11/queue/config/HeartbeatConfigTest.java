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
}
