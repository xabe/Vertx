package com.xabe.vertx.service.mutiny;

import java.util.Map;
import java.util.stream.Collectors;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.util.function.Consumer;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Publisher;
import io.smallrye.mutiny.vertx.TypeArg;
import io.vertx.codegen.annotations.Fluent;
import com.xabe.vertx.service.Beer;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

@io.smallrye.mutiny.vertx.MutinyGen(com.xabe.vertx.service.BarmanService.class)
public class BarmanService {

  public static final io.smallrye.mutiny.vertx.TypeArg<BarmanService> __TYPE_ARG = new io.smallrye.mutiny.vertx.TypeArg<>(    obj -> new BarmanService((com.xabe.vertx.service.BarmanService) obj),
    BarmanService::getDelegate
  );

  private final com.xabe.vertx.service.BarmanService delegate;
  
  public BarmanService(com.xabe.vertx.service.BarmanService delegate) {
    this.delegate = delegate;
  }

  public BarmanService(Object delegate) {
    this.delegate = (com.xabe.vertx.service.BarmanService)delegate;
  }

  /**
   * Empty constructor used by CDI, do not use this constructor directly.
   **/
  BarmanService() {
    this.delegate = null;
  }

  public com.xabe.vertx.service.BarmanService getDelegate() {
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
    BarmanService that = (BarmanService) o;
    return delegate.equals(that.delegate);
  }
  
  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  public io.smallrye.mutiny.Uni<com.xabe.vertx.service.Beer> giveMeARandomBeer(String customerName) { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(handler -> {
        delegate.giveMeARandomBeer(customerName, handler);
    });
  }

  public com.xabe.vertx.service.Beer giveMeARandomBeerAndAwait(String customerName) { 
    return (com.xabe.vertx.service.Beer) giveMeARandomBeer(customerName).await().indefinitely();
  }

  public void giveMeARandomBeerAndForget(String customerName) { 
    giveMeARandomBeer(customerName).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  public io.smallrye.mutiny.Uni<Integer> getMyBill(String customerName) { 
    return io.smallrye.mutiny.vertx.AsyncResultUni.toUni(handler -> {
        delegate.getMyBill(customerName, handler);
    });
  }

  public Integer getMyBillAndAwait(String customerName) { 
    return (Integer) getMyBill(customerName).await().indefinitely();
  }

  public void getMyBillAndForget(String customerName) { 
    getMyBill(customerName).subscribe().with(io.smallrye.mutiny.vertx.UniHelper.NOOP);
  }

  public void payMyBill(String customerName) { 
    delegate.payMyBill(customerName);
  }

  public static  BarmanService newInstance(com.xabe.vertx.service.BarmanService arg) {
    return arg != null ? new BarmanService(arg) : null;
  }

}
