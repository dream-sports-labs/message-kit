package com.dream11.queue.config;

import com.dream11.queue.QueueProvider;

public interface QueueConfig {
  /**
   * Returns the provider type for this configuration.
   *
   * @return The QueueProvider type.
   */
  QueueProvider getProvider();
  /**
   * Returns the heartbeat configuration for this configuration.
   *
   * @return An instance of HeartbeatConfig.
   */
  HeartbeatConfig getHeartbeatConfig();
}
