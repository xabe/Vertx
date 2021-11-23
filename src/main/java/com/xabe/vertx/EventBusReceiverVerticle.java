package com.xabe.vertx;

import com.xabe.vertx.data.Article;
import com.xabe.vertx.data.ArticleMessageCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusReceiverVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestServiceVerticle.class);

  public static final String AN_ADDRESS = "anAddressReceiver";

  public static final String ARTICLE = "articleReceiver";

  @Override
  public void start() {
    final EventBus eventBus = this.getVertx().eventBus();
    // Register codec for custom message
    eventBus.registerDefaultCodec(Article.class, new ArticleMessageCodec());

    eventBus.consumer(AN_ADDRESS, message -> LOGGER.info("1 received message.body() = {}", message.body()));
    eventBus.consumer(ARTICLE, message -> LOGGER.info("1 received message.body() = {}", message.body()));
  }

}
