package com.xabe.vertx;

import static com.xabe.vertx.EventBusSenderVerticle.AN_ADDRESS;
import static com.xabe.vertx.EventBusSenderVerticle.ARTICLE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;

@ExtendWith(VertxExtension.class)
public class EventBusSenderVerticleTest {

  @BeforeEach
  @DisplayName("Deploy a verticle")
  void prepare(final Vertx vertx, final VertxTestContext testContext) throws IOException {
    final DeploymentOptions options = new DeploymentOptions();
    vertx.deployVerticle(new EventBusSenderVerticle(), options, testContext.succeedingThenComplete());
  }

  @Test
  @DisplayName("A first test event bus send string")
  void eventBusSendString(final Vertx vertx, final VertxTestContext testContext) {
    vertx.eventBus().consumer(AN_ADDRESS,
        message -> LoggerFactory.getLogger("EventBusSenderVerticleTest").info("1 received message.body() = {}", message.body()));
    testContext.completeNow();
  }

  @Test
  @DisplayName("A first test event bus send Article")
  void eventBusSendArticle(final Vertx vertx, final VertxTestContext testContext) {
    vertx.eventBus().consumer(ARTICLE,
        message -> LoggerFactory.getLogger("EventBusSenderVerticleTest").info("1 received message.body() = {}", message.body()));
    testContext.completeNow();
  }

  @AfterEach
  @DisplayName("Check that the verticle is still there")
  void lastChecks(final Vertx vertx) {
    assertThat(vertx, is(notNullValue()));
    assertThat(vertx.deploymentIDs(), is(hasSize(1)));
  }

}
