package com.dream11.queue.producer;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for producing messages to a message queue. Implementations of this interface handle the
 * specifics of sending messages to different message queue providers.
 *
 * @param <T> The type of message that this producer will handle.
 */
public interface MessageProducer<T> extends AutoCloseable {
  /**
   * Sends a message asynchronously to the message queue.
   *
   * @param message The message to send.
   * @return A CompletableFuture that completes when the message is sent.
   */
  CompletableFuture<Void> send(T message);

  /**
   * Closes the message producer, releasing any resources. This method should be called when the
   * producer is no longer needed.
   */
  void close();
}
