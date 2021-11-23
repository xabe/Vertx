package com.xabe.vertx;

import java.io.IOException;

import com.xabe.vertx.service.BarmanService;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class BarmanVerticleTest {

  private BarmanService barmanService;

  @BeforeEach
  @DisplayName("Deploy a verticle")
  void prepare(final Vertx vertx, final VertxTestContext testContext) throws IOException {
    final DeploymentOptions options = new DeploymentOptions();
    vertx.deployVerticle(new BarmanVerticle(), options, testContext.succeedingThenComplete());
    this.barmanService = BarmanService.createProxy(vertx, BarmanVerticle.BEERS_SERVICES_MYAPPLICATION_ADDRESS);
  }

  @Test
  @DisplayName("A tell barman")
  void foo(final Vertx vertx, final VertxTestContext testContext) {
    this.barmanService.giveMeARandomBeer("homer", b1 -> { // (2)
      if (b1.failed()) { // (3)
        System.err.println("Cannot get my first beer!");
        testContext.failNow(b1.cause());
        return;
      }
      System.out.println("My first beer is a " + b1.result() + " and it costs " + b1.result().getPrice() + "€"); // (4)
      vertx.setTimer(1500, l ->
          this.barmanService.giveMeARandomBeer("homer", b2 -> { // (5)
            if (b2.failed()) {
              System.out.println("Cannot get my second beer!");
              testContext.failNow(b2.cause());
              return;
            }
            System.out.println("My second beer is a " + b2.result() + " and it costs " + b2.result().getPrice() + "€"); // (6)
            this.barmanService.getMyBill("homer", billAr -> {
              System.out.println("My bill with the bar is " + billAr.result()); // (7)
              this.barmanService.payMyBill("homer"); // (8)
              testContext.completeNow();
            });
          })
      );
    });
  }

}
