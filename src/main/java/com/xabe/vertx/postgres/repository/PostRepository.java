package com.xabe.vertx.postgres.repository;

import java.util.List;

import com.xabe.vertx.postgres.repository.data.Post;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgPool;

@VertxGen
@ProxyGen
public interface PostRepository {

  @Fluent
  PostRepository findAll(Handler<AsyncResult<List<Post>>> resultHandler);

  @Fluent
  PostRepository findById(final String id, Handler<AsyncResult<Post>> resultHandler);

  @Fluent
  PostRepository save(final Post data, Handler<AsyncResult<String>> resultHandler);

  @Fluent
  PostRepository update(final Post data, Handler<AsyncResult<Integer>> resultHandler);

  @Fluent
  PostRepository deleteAll(Handler<AsyncResult<Integer>> resultHandler);

  @Fluent
  PostRepository deleteById(final String id, Handler<AsyncResult<Integer>> resultHandler);

  @GenIgnore
  static PostRepository create(final PgPool pgPool) {
    return new PostRepositoryImpl(pgPool);
  }

  @GenIgnore
  static PostRepository createProxy(final Vertx vertx, final String address) {
    return new PostRepositoryVertxEBProxy(vertx, address);
  }

}
