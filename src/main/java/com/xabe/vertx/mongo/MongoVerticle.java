package com.xabe.vertx.mongo;

import com.xabe.vertx.mongo.repository.CustomerRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.serviceproxy.ServiceBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(MongoVerticle.class);

  public static final String REPOSITORY_ADDRESS = "mongo.repository";

  private MongoClient mongoClient;

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    final JsonObject jsonObject = new JsonObject();
    jsonObject.put("host", "localhost");
    jsonObject.put("port", 27017);
    jsonObject.put("db_name", "customerdb");
    this.mongoClient = MongoClient.createShared(this.vertx, jsonObject);
    final CustomerRepository customerRepository = CustomerRepository.create(this.mongoClient);
    new ServiceBinder(this.vertx)
        .setAddress(REPOSITORY_ADDRESS)
        .register(CustomerRepository.class, customerRepository).exceptionHandler(throwable -> {
          LOGGER.error("Failed to establish Mongo database service", throwable);
          startPromise.fail(throwable);
        })
        .completionHandler(res -> {
          LOGGER.info("Mongo database service is successfully established in customer");
          startPromise.complete();
        });
  }

  @Override
  public void stop() throws Exception {
    this.mongoClient.close();
  }
}
