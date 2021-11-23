package com.xabe.vertx;

import io.vertx.core.AbstractVerticle;

public class SimpleServerVerticle extends AbstractVerticle {

  @Override
  public void start() {
    this.vertx.createHttpServer()
        .requestHandler(
            r -> r.response().end("Welcome to Vert.x Intro"))
        .listen(this.config().getInteger("http.port", 8080));
  }

}
