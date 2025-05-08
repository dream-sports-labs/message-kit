package com.dream11.queue.producer;

import static org.assertj.core.api.Assertions.assertThat;

import com.dream11.queue.impl.sqs.SqsConfig;
import com.dream11.queue.impl.sqs.SqsProducer;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MessageProducerFactoryTest {

  @Test
  void testProducerCreateWhenConfigTypeIsSqs() {
    // Arrange
    SqsConfig sqsConfig = SqsConfig.builder().region("us-east-1").queueUrl("").build();

    // Act
    MessageProducer<String> messageProducer = MessageProducerFactory.create(sqsConfig);

    // Assert
    assertThat(messageProducer).isInstanceOf(SqsProducer.class);
  }

  @ParameterizedTest
  @MethodSource("endpoints")
  void testProducerCreateWhenConfigTypeIsSqsWithEndpointOverride(String endpoint) {
    // Arrange
    SqsConfig sqsConfig =
        SqsConfig.builder().region("us-east-1").queueUrl("queue").endpoint(endpoint).build();

    // Act
    MessageProducer<String> messageProducer = MessageProducerFactory.create(sqsConfig);

    // Assert
    assertThat(messageProducer).isInstanceOf(SqsProducer.class);
  }

  private static Stream<Arguments> endpoints() {
    return Stream.of(Arguments.of("http://dummyEndpoint"), Arguments.of(""));
  }
}
