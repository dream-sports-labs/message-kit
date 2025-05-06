package com.dream11.queue.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Configuration class for heartbeat settings in message queue consumers. */
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class HeartbeatConfig {
  /**
   * The interval in seconds between heartbeats. A value of -1 indicates that heartbeats are
   * disabled.
   */
  @Builder.Default private Integer heartbeatInterval = -1; // in seconds

  /**
   * The number of threads in the executor pool used for sending heartbeats. This determines how
   * many concurrent heartbeat operations can be performed.
   */
  @Builder.Default private Integer executorThreadPoolSize = 2;
}
