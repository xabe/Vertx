package com.xabe.vertx.hibernate.repository.mutiny;

import java.util.Map;
import java.util.stream.Collectors;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.util.function.Consumer;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Publisher;
import io.smallrye.mutiny.vertx.TypeArg;
import io.vertx.codegen.annotations.Fluent;
import java.util.List;
import com.xabe.vertx.hibernate.repository.data.Person;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

@io.smallrye.mutiny.vertx.MutinyGen(com.xabe.vertx.hibernate.repository.PersonRepository.class)
public class PersonRepository {

  public static final io.smallrye.mutiny.vertx.TypeArg<PersonRepository> __TYPE_ARG = new io.smallrye.mutiny.vertx.TypeArg<>(    obj -> new PersonRepository((com.xabe.vertx.hibernate.repository.PersonRepository) obj),
    PersonRepository::getDelegate
  );

  private final com.xabe.vertx.hibernate.repository.PersonRepository delegate;
  
  public PersonRepository(com.xabe.vertx.hibernate.repository.PersonRepository delegate) {
    this.delegate = delegate;
  }

  public PersonRepository(Object delegate) {
    this.delegate = (com.xabe.vertx.hibernate.repository.PersonRepository)delegate;
  }

  /**
   * Empty constructor used by CDI, do not use this constructor directly.
   **/
  PersonRepository() {
    this.delegate = null;
  }

  public com.xabe.vertx.hibernate.repository.PersonRepository getDelegate() {
    return delegate;
  }

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PersonRepository that = (PersonRepository) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  public io.smallrye.mutiny.Uni<List<com.xabe.vertx.hibernate.repository.data.Person>> findAll() { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.findAll(resultHandler);
    });
  }

  public List<com.xabe.vertx.hibernate.repository.data.Person> findAllAndAwait() { 
    return (List<com.xabe.vertx.hibernate.repository.data.Person>) findAll().await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.hibernate.repository.mutiny.PersonRepository findAllAndForget() { 
    findAll().subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public io.smallrye.mutiny.Uni<com.xabe.vertx.hibernate.repository.data.Person> findById(String id) { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.findById(id, resultHandler);
    });
  }

  public com.xabe.vertx.hibernate.repository.data.Person findByIdAndAwait(String id) { 
    return (com.xabe.vertx.hibernate.repository.data.Person) findById(id).await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.hibernate.repository.mutiny.PersonRepository findByIdAndForget(String id) { 
    findById(id).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public io.smallrye.mutiny.Uni<String> save(com.xabe.vertx.hibernate.repository.data.Person person) { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.save(person, resultHandler);
    });
  }

  public String saveAndAwait(com.xabe.vertx.hibernate.repository.data.Person person) { 
    return (String) save(person).await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.hibernate.repository.mutiny.PersonRepository saveAndForget(com.xabe.vertx.hibernate.repository.data.Person person) { 
    save(person).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public io.smallrye.mutiny.Uni<Void> update(com.xabe.vertx.hibernate.repository.data.Person person) { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.update(person, resultHandler);
    });
  }

  public Void updateAndAwait(com.xabe.vertx.hibernate.repository.data.Person person) { 
    return (Void) update(person).await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.hibernate.repository.mutiny.PersonRepository updateAndForget(com.xabe.vertx.hibernate.repository.data.Person person) { 
    update(person).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public io.smallrye.mutiny.Uni<Integer> deleteAll() { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.deleteAll(resultHandler);
    });
  }

  public Integer deleteAllAndAwait() { 
    return (Integer) deleteAll().await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.hibernate.repository.mutiny.PersonRepository deleteAllAndForget() { 
    deleteAll().subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public io.smallrye.mutiny.Uni<Integer> deleteById(String id) { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.deleteById(id, resultHandler);
    });
  }

  public Integer deleteByIdAndAwait(String id) { 
    return (Integer) deleteById(id).await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.hibernate.repository.mutiny.PersonRepository deleteByIdAndForget(String id) { 
    deleteById(id).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public static  PersonRepository newInstance(com.xabe.vertx.hibernate.repository.PersonRepository arg) {
    return arg != null ? new PersonRepository(arg) : null;
  }

}
