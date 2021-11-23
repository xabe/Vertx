package com.xabe.vertx;

import com.xabe.vertx.service.BarmanService;
import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ServiceBinder;

public class BarmanVerticle extends AbstractVerticle {

  public static final String BEERS_SERVICES_MYAPPLICATION_ADDRESS = "beers.services.myapplication";

  @Override
  public void start() {
    final BarmanService service = BarmanService.create(this.vertx);
    new ServiceBinder(this.vertx)
        .setAddress(BEERS_SERVICES_MYAPPLICATION_ADDRESS)
        .register(BarmanService.class, service);
  }

}
