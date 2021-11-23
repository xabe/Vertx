package com.xabe.vertx;

import com.xabe.vertx.data.Article;
import com.xabe.vertx.data.ArticleMessageCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class EventBusSenderVerticle extends AbstractVerticle {

  public static final String AN_ADDRESS = "anAddressSender";

  public static final String ARTICLE = "articleSender";

  @Override
  public void start() {
    final EventBus eventBus = this.getVertx().eventBus();
    // Register codec for custom message
    eventBus.registerDefaultCodec(Article.class, new ArticleMessageCodec());

    eventBus.send(AN_ADDRESS, "message");
    final Article article = new Article("id", "This is an intro to vertx", "xabe", "01-02-2017", 1578);
    eventBus.send(ARTICLE, article);
  }

}
