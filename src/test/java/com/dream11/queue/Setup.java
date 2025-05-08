package com.dream11.queue;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public class Setup
    implements BeforeAllCallback, AfterAllCallback, ExtensionContext.Store.CloseableResource {
  static boolean started = false;
  public LocalStackContainer localStackContainer;

  @Override
  public void afterAll(ExtensionContext extensionContext) {}

  @Override
  public void beforeAll(ExtensionContext extensionContext) {
    if (!started) {
      DockerImageName localstackImage = DockerImageName.parse(Constants.LOCALSTACK_DOCKER_IMAGE);
      localStackContainer =
          new LocalStackContainer(localstackImage)
              .withServices(LocalStackContainer.Service.SQS)
              .withStartupTimeout(Duration.ofSeconds(600));

      localStackContainer.start();

      String port = localStackContainer.getFirstMappedPort().toString();
      log.info("Started localstack sqs container on port:{}", port);

      System.setProperty(
          Constants.SQS_ENDPOINT,
          localStackContainer.getEndpointOverride(LocalStackContainer.Service.SQS).toString());
      System.setProperty(Constants.AWS_REGION, localStackContainer.getRegion());
      System.setProperty(Constants.AWS_ACCESS_KEY_ID, localStackContainer.getAccessKey());
      System.setProperty(Constants.AWS_SECRET_ACCESS_KEY, localStackContainer.getSecretKey());

      started = true;
      extensionContext.getRoot().getStore(GLOBAL).put("test", this);
    }
  }

  @Override
  public void close() {
    this.localStackContainer.close();
  }
}
