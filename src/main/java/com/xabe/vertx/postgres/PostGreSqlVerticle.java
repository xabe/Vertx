package com.xabe.vertx.postgres;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xabe.vertx.postgres.repository.PostRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.serviceproxy.ServiceBinder;
import io.vertx.sqlclient.PoolOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostGreSqlVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(PostGreSqlVerticle.class);

  public static final String REPOSITORY_ADDRESS = "postgres.repository";

  private PgPool pgPool;

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    final ObjectMapper objectMapper = DatabindCodec.mapper();
    objectMapper.registerModule(new JavaTimeModule());
    final PgConnectOptions connectOptions = new PgConnectOptions()
        .setPort(5432)
        .setHost("localhost")
        .setDatabase("blogdb")
        .setUser("user")
        .setPassword("password");

    // Pool Options
    final PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

    // Create the pool from the data object
    this.pgPool = PgPool.pool(this.vertx, connectOptions, poolOptions);
    final PostRepository postRepository = PostRepository.create(this.pgPool);
    new ServiceBinder(this.vertx)
        .setAddress(REPOSITORY_ADDRESS)
        .register(PostRepository.class, postRepository).exceptionHandler(throwable -> {
          LOGGER.error("Failed to establish PostgreSQL database service", throwable);
          startPromise.fail(throwable);
        })
        .completionHandler(res -> {
          LOGGER.info("PostgreSQL database service is successfully established in blogdb");
          startPromise.complete();
        });
    ;
  }

  @Override
  public void stop() throws Exception {
    this.pgPool.close();
  }
}
