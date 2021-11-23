package com.xabe.vertx;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import com.xabe.vertx.data.Article;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class EventBusReceiverVerticleTest {

  @BeforeEach
  @DisplayName("Deploy a verticle")
  void prepare(final Vertx vertx, final VertxTestContext testContext) throws IOException {
    final DeploymentOptions options = new DeploymentOptions();
    vertx.deployVerticle(new EventBusReceiverVerticle(), options, testContext.succeedingThenComplete());
  }

  @Test
  @DisplayName("A first test event bus reciver string")
  void eventBusReciverString(final Vertx vertx, final VertxTestContext testContext) {
    vertx.eventBus().publish(EventBusReceiverVerticle.AN_ADDRESS, "message 2");
    vertx.eventBus().send(EventBusReceiverVerticle.AN_ADDRESS, "message 1");
    testContext.completeNow();
  }

  @Test
  @DisplayName("A first test event bus reciver Article")
  void eventBusReciverArticle(final Vertx vertx, final VertxTestContext testContext) {
    final Article article = new Article("id", "This is an intro to vertx", "xabe", "01-02-2017", 1578);
    vertx.eventBus().publish(EventBusReceiverVerticle.ARTICLE, article);
    vertx.eventBus().send(EventBusReceiverVerticle.ARTICLE, article);
    testContext.completeNow();
  }

  @AfterEach
  @DisplayName("Check that the verticle is still there")
  void lastChecks(final Vertx vertx) {
    assertThat(vertx, is(notNullValue()));
    assertThat(vertx.deploymentIDs(), is(hasSize(1)));
  }

}
