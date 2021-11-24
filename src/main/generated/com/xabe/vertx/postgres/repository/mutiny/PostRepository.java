package com.xabe.vertx.postgres.repository.mutiny;

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
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import com.xabe.vertx.postgres.repository.data.Post;

@io.smallrye.mutiny.vertx.MutinyGen(com.xabe.vertx.postgres.repository.PostRepository.class)
public class PostRepository {

  public static final io.smallrye.mutiny.vertx.TypeArg<PostRepository> __TYPE_ARG = new io.smallrye.mutiny.vertx.TypeArg<>(    obj -> new PostRepository((com.xabe.vertx.postgres.repository.PostRepository) obj),
    PostRepository::getDelegate
  );

  private final com.xabe.vertx.postgres.repository.PostRepository delegate;
  
  public PostRepository(com.xabe.vertx.postgres.repository.PostRepository delegate) {
    this.delegate = delegate;
  }

  public PostRepository(Object delegate) {
    this.delegate = (com.xabe.vertx.postgres.repository.PostRepository)delegate;
  }

  /**
   * Empty constructor used by CDI, do not use this constructor directly.
   **/
  PostRepository() {
    this.delegate = null;
  }

  public com.xabe.vertx.postgres.repository.PostRepository getDelegate() {
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
    PostRepository that = (PostRepository) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  public io.smallrye.mutiny.Uni<List<com.xabe.vertx.postgres.repository.data.Post>> findAll() { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.findAll(resultHandler);
    });
  }

  public List<com.xabe.vertx.postgres.repository.data.Post> findAllAndAwait() { 
    return (List<com.xabe.vertx.postgres.repository.data.Post>) findAll().await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.postgres.repository.mutiny.PostRepository findAllAndForget() { 
    findAll().subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public io.smallrye.mutiny.Uni<com.xabe.vertx.postgres.repository.data.Post> findById(String id) { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.findById(id, resultHandler);
    });
  }

  public com.xabe.vertx.postgres.repository.data.Post findByIdAndAwait(String id) { 
    return (com.xabe.vertx.postgres.repository.data.Post) findById(id).await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.postgres.repository.mutiny.PostRepository findByIdAndForget(String id) { 
    findById(id).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public io.smallrye.mutiny.Uni<String> save(com.xabe.vertx.postgres.repository.data.Post data) { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.save(data, resultHandler);
    });
  }

  public String saveAndAwait(com.xabe.vertx.postgres.repository.data.Post data) { 
    return (String) save(data).await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.postgres.repository.mutiny.PostRepository saveAndForget(com.xabe.vertx.postgres.repository.data.Post data) { 
    save(data).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public io.smallrye.mutiny.Uni<Integer> update(com.xabe.vertx.postgres.repository.data.Post data) { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(resultHandler -> {
        delegate.update(data, resultHandler);
    });
  }

  public Integer updateAndAwait(com.xabe.vertx.postgres.repository.data.Post data) { 
    return (Integer) update(data).await().indefinitely();
  }

  @Fluent
  public com.xabe.vertx.postgres.repository.mutiny.PostRepository updateAndForget(com.xabe.vertx.postgres.repository.data.Post data) { 
    update(data).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
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
  public com.xabe.vertx.postgres.repository.mutiny.PostRepository deleteAllAndForget() { 
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
  public com.xabe.vertx.postgres.repository.mutiny.PostRepository deleteByIdAndForget(String id) { 
    deleteById(id).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
    return this;
  }

  public static  PostRepository newInstance(com.xabe.vertx.postgres.repository.PostRepository arg) {
    return arg != null ? new PostRepository(arg) : null;
  }

}
