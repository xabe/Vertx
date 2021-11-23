package com.xabe.vertx.service;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

@VertxGen
@ProxyGen
public interface BarmanService {

  void giveMeARandomBeer(String customerName, Handler<AsyncResult<Beer>> handler);

  void getMyBill(String customerName, Handler<AsyncResult<Integer>> handler);

  void payMyBill(String customerName);

  @GenIgnore
  static BarmanService create(final Vertx vertx) {
    return new BarmanServiceImpl(WebClient.create(vertx));
  }

  @GenIgnore
  static BarmanService createProxy(final Vertx vertx, final String address) { // (5)
    return new BarmanServiceVertxEBProxy(vertx, address);
  }

}
