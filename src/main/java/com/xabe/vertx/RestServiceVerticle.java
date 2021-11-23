package com.xabe.vertx;

import com.xabe.vertx.data.Article;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestServiceVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestServiceVerticle.class);

  public static final String APPLICATION_JSON = "application/json";

  @Override
  public void start() {

    final Router router = Router.router(this.vertx);
    router.get("/api/articles/article/:id")
        .produces(APPLICATION_JSON)
        .handler(this::getArticles);

    this.vertx.createHttpServer()
        .requestHandler(router)
        .listen(this.config().getInteger("http.port", 8080))
        .onSuccess(server -> LOGGER.info("HTTP server started on port {}", server.actualPort()));
  }

  private void getArticles(final RoutingContext routingContext) {
    final String articleId = routingContext.request()
        .getParam("id");
    final Article article = new Article(articleId, "This is an intro to vertx", "xabe", "01-02-2017", 1578);

    routingContext.response()
        .putHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
        .setStatusCode(HttpResponseStatus.OK.code())
        .end(Json.encodePrettily(article));
  }

}
