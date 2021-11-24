package com.xabe.vertx.hibernate.repository;

import java.util.List;
import java.util.UUID;

import com.xabe.vertx.hibernate.repository.data.Person;
import io.smallrye.mutiny.vertx.UniHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory;

public class PersonRepositoryImpl implements PersonRepository {

  private final SessionFactory sessionFactory;

  public PersonRepositoryImpl(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public PersonRepository findAll(final Handler<AsyncResult<List<Person>>> resultHandler) {
    this.sessionFactory.withSession(session -> session.find(Person.class)).subscribe()
        .withSubscriber(UniHelper.toSubscriber(resultHandler));
    return this;
  }

  @Override
  public PersonRepository findById(final String id, final Handler<AsyncResult<Person>> resultHandler) {
    this.sessionFactory.withSession(session -> session.find(Person.class, UUID.fromString(id))).subscribe()
        .withSubscriber(UniHelper.toSubscriber(resultHandler));
    return this;
  }

  @Override
  public PersonRepository save(final Person person, final Handler<AsyncResult<String>> resultHandler) {
    this.sessionFactory.withSession(session -> session.persist(person).chain(session::flush).replaceWith(person))
        .map(Person::getId)
        .map(UUID::toString)
        .subscribe()
        .withSubscriber(UniHelper.toSubscriber(resultHandler));
    return this;
  }

  @Override
  public PersonRepository update(final Person person, final Handler<AsyncResult<Void>> resultHandler) {
    this.sessionFactory.withSession(session -> session.merge(person).onItem().call(session::flush)).replaceWithVoid().subscribe()
        .withSubscriber(UniHelper.toSubscriber(resultHandler));
    return this;
  }

  @Override
  public PersonRepository deleteAll(final Handler<AsyncResult<Integer>> resultHandler) {
    this.sessionFactory.withTransaction((session, tx) -> session.createQuery("delete Person").executeUpdate())
        .subscribe()
        .withSubscriber(UniHelper.toSubscriber(resultHandler));
    return this;
  }

  @Override
  public PersonRepository deleteById(final String id, final Handler<AsyncResult<Integer>> resultHandler) {
    this.sessionFactory.withTransaction(
            (session, tx) -> session.createQuery("delete from Person where id= :uid").setParameter("uid", UUID.fromString(id)).executeUpdate())
        .subscribe()
        .withSubscriber(UniHelper.toSubscriber(resultHandler));
    return this;
  }
}
