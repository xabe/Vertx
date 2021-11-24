package com.xabe.vertx.hibernate.repository;

import java.util.List;

import com.xabe.vertx.hibernate.repository.data.Person;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory;

@VertxGen
@ProxyGen
public interface PersonRepository {

  @Fluent
  PersonRepository findAll(Handler<AsyncResult<List<Person>>> resultHandler);

  @Fluent
  PersonRepository findById(final String id, Handler<AsyncResult<Person>> resultHandler);

  @Fluent
  PersonRepository save(final Person person, Handler<AsyncResult<String>> resultHandler);

  @Fluent
  PersonRepository update(final Person person, Handler<AsyncResult<Void>> resultHandler);

  @Fluent
  PersonRepository deleteAll(Handler<AsyncResult<Integer>> resultHandler);

  @Fluent
  PersonRepository deleteById(final String id, Handler<AsyncResult<Integer>> resultHandler);

  @GenIgnore
  static PersonRepository create(final SessionFactory emf) {
    return new PersonRepositoryImpl(emf);
  }

  @GenIgnore
  static PersonRepository createProxy(final Vertx vertx, final String address) {
    return new PersonRepositoryVertxEBProxy(vertx, address);
  }

}
