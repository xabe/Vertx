package com.xabe.vertx;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import java.io.IOException;
import java.net.ServerSocket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class SimpleServerVerticleTest {

  private int port = 8081;

  @BeforeEach
  @DisplayName("Deploy a verticle")
  void prepare(final Vertx vertx, final VertxTestContext testContext) throws IOException {
    // Pick an available and random
    final ServerSocket socket = new ServerSocket(0);
    this.port = socket.getLocalPort();
    socket.close();

    final DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", this.port));

    vertx.deployVerticle(new SimpleServerVerticle(), options, testContext.succeedingThenComplete());
  }

  @Test
  @DisplayName("A first test")
  void foo(final Vertx vertx, final VertxTestContext testContext) {
    final HttpClient client = vertx.createHttpClient();
    client.request(HttpMethod.GET, this.port, "localhost", "/")
        .compose(req -> req.send().compose(HttpClientResponse::body))
        .onComplete(testContext.succeeding(buffer -> testContext.verify(() -> {
          assertThat(buffer.toString(), is("Welcome to Vert.x Intro"));
          testContext.completeNow();
        })));
  }

  @AfterEach
  @DisplayName("Check that the verticle is still there")
  void lastChecks(final Vertx vertx) {
    assertThat(vertx, is(notNullValue()));
    assertThat(vertx.deploymentIDs(), is(hasSize(1)));
  }

}