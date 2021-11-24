package com.xabe.vertx.mongo.repository.mutiny;

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
import com.xabe.vertx.mongo.repository.data.Customer;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

@io.smallrye.mutiny.vertx.MutinyGen(com.xabe.vertx.mongo.repository.CustomerRepository.class)
public class CustomerRepository {

  public static final io.smallrye.mutiny.vertx.TypeArg<CustomerRepository> __TYPE_ARG = new io.smallrye.mutiny.vertx.TypeArg<>(    obj -> new CustomerRepository((com.xabe.vertx.mongo.repository.CustomerRepository) obj),
    CustomerRepository::getDelegate
  );

  private final com.xabe.vertx.mongo.repository.CustomerRepository delegate;
  
  public CustomerRepository(com.xabe.vertx.mongo.repository.CustomerRepository delegate) {
    this.delegate = delegate;
  }

  public CustomerRepository(Object delegate) {
    this.delegate = (com.xabe.vertx.mongo.repository.CustomerRepository)delegate;
  }

  /**
   * Empty constructor used by CDI, do not use this constructor directly.
   **/
  CustomerRepository() {
    this.delegate = null;
  }

  public com.xabe.vertx.mongo.repository.CustomerRepository getDelegate() {
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
    CustomerRepository that = (CustomerRepository) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  public io.smallrye.mutiny.Uni<List<com.xabe.vertx.mongo.repository.data.Customer>> findAll() { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.findAll(resultHandler);
    });
  }

  public List<com.xabe.vertx.mongo.repository.data.Customer> findAllAndAwait() { 
    return (List<com.xabe.vertx.mongo.repository.data.Customer>) findAll().await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.mongo.repository.mutiny.CustomerRepository findAllAndForget() { 
    findAll().subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public io.smallrye.mutiny.Uni<com.xabe.vertx.mongo.repository.data.Customer> findById(String id) { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.findById(id, resultHandler);
    });
  }

  public com.xabe.vertx.mongo.repository.data.Customer findByIdAndAwait(String id) { 
    return (com.xabe.vertx.mongo.repository.data.Customer) findById(id).await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.mongo.repository.mutiny.CustomerRepository findByIdAndForget(String id) { 
    findById(id).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public io.smallrye.mutiny.Uni<String> save(com.xabe.vertx.mongo.repository.data.Customer customer) { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.save(customer, resultHandler);
    });
  }

  public String saveAndAwait(com.xabe.vertx.mongo.repository.data.Customer customer) { 
    return (String) save(customer).await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.mongo.repository.mutiny.CustomerRepository saveAndForget(com.xabe.vertx.mongo.repository.data.Customer customer) { 
    save(customer).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public io.smallrye.mutiny.Uni<Long> update(com.xabe.vertx.mongo.repository.data.Customer customer) { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.update(customer, resultHandler);
    });
  }

  public Long updateAndAwait(com.xabe.vertx.mongo.repository.data.Customer customer) { 
    return (Long) update(customer).await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.mongo.repository.mutiny.CustomerRepository updateAndForget(com.xabe.vertx.mongo.repository.data.Customer customer) { 
    update(customer).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public io.smallrye.mutiny.Uni<Long> deleteAll() { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.deleteAll(resultHandler);
    });
  }

  public Long deleteAllAndAwait() { 
    return (Long) deleteAll().await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.mongo.repository.mutiny.CustomerRepository deleteAllAndForget() { 
    deleteAll().subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public io.smallrye.mutiny.Uni<Long> deleteById(String id) { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.deleteById(id, resultHandler);
    });
  }

  public Long deleteByIdAndAwait(String id) { 
    return (Long) deleteById(id).await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.mongo.repository.mutiny.CustomerRepository deleteByIdAndForget(String id) { 
    deleteById(id).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public static  CustomerRepository newInstance(com.xabe.vertx.mongo.repository.CustomerRepository arg) {
    return arg != null ? new CustomerRepository(arg) : null;
  }

}
