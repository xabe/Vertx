package com.xabe.vertx.mongo.repository;

import java.util.List;

import com.xabe.vertx.mongo.repository.data.Customer;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.mongo.MongoClient;

@VertxGen
@ProxyGen
public interface CustomerRepository {

  @Fluent
  CustomerRepository findAll(Handler<AsyncResult<List<Customer>>> resultHandler);

  @Fluent
  CustomerRepository findById(final String id, Handler<AsyncResult<Customer>> resultHandler);

  @Fluent
  CustomerRepository save(final Customer customer, Handler<AsyncResult<String>> resultHandler);

  @Fluent
  CustomerRepository update(final Customer customer, Handler<AsyncResult<Long>> resultHandler);

  @Fluent
  CustomerRepository deleteAll(Handler<AsyncResult<Long>> resultHandler);

  @Fluent
  CustomerRepository deleteById(final String id, Handler<AsyncResult<Long>> resultHandler);

  @GenIgnore
  static CustomerRepository create(final MongoClient mongoClient) {
    return new CustomerRepositoryImpl(mongoClient);
  }

  @GenIgnore
  static CustomerRepository createProxy(final Vertx vertx, final String address) {
    return new CustomerRepositoryVertxEBProxy(vertx, address);
  }

}
