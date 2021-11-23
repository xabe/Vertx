package com.xabe.vertx.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarmanServiceImpl implements BarmanService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BarmanServiceImpl.class);

  private final WebClient webClient;

  private final Random random;

  private final Map<String, Integer> bills;

  public BarmanServiceImpl(final WebClient webClient) {
    this.webClient = webClient;
    this.random = new Random();
    this.bills = new HashMap<>();
  }

  @Override
  public void giveMeARandomBeer(final String customerName, final Handler<AsyncResult<Beer>> handler) {
    this.webClient
        .get(443, "www.craftbeernamegenerator.com", "/api/api.php?type=classic")
        .ssl(true)
        .send(ar -> { // (1)
          if (ar.failed()) {
            handler.handle(Future.failedFuture(ar.cause())); // (2)
          } else {
            final JsonObject result = ar.result().bodyAsJsonObject();
            if (result.getInteger("status") != 200) // (2)
            {
              handler.handle(Future.failedFuture(
                  "Beer Generator Service replied with " + result.getInteger("status") + ": " + result.getString("status_message")));
            } else {
              final Beer beer = new Beer(
                  result.getJsonObject("data").getString("name"),
                  result.getJsonObject("data").getString("style"),
                  3 + this.random.nextInt(5)
              );
              LOGGER.info("Generated a new Beer! {}", beer);
              this.bills.merge(customerName, beer.getPrice(), (oldVal, newVal) -> oldVal + newVal);
              handler.handle(Future.succeededFuture(beer));
            }
          }
        });
  }

  @Override
  public void getMyBill(final String customerName, final Handler<AsyncResult<Integer>> handler) {
    handler.handle(Future.succeededFuture(this.bills.get(customerName)));
  }

  @Override
  public void payMyBill(final String customerName) {
    this.bills.remove(customerName);
    LOGGER.info("Removed debt of  {}", customerName);
  }
}
