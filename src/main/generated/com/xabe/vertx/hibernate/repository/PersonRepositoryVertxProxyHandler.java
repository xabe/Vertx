/*
* Copyright 2014 Red Hat, Inc.
*
* Red Hat licenses this file to you under the Apache License, version 2.0
* (the "License"); you may not use this file except in compliance with the
* License. You may obtain a copy of the License at:
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations
* under the License.
*/

package com.xabe.vertx.hibernate.repository;

import com.xabe.vertx.hibernate.repository.PersonRepository;
import io.vertx.core.Vertx;
import io.vertx.core.Handler;
import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import io.vertx.serviceproxy.ProxyHandler;
import io.vertx.serviceproxy.ServiceException;
import io.vertx.serviceproxy.ServiceExceptionMessageCodec;
import io.vertx.serviceproxy.HelperUtils;
import io.vertx.serviceproxy.ServiceBinder;

import com.xabe.vertx.hibernate.repository.PersonRepository;
import java.util.List;
import com.xabe.vertx.hibernate.repository.data.Person;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
/*
  Generated Proxy code - DO NOT EDIT
  @author Roger the Robot
*/

@SuppressWarnings({"unchecked", "rawtypes"})
public class PersonRepositoryVertxProxyHandler extends ProxyHandler {

  public static final long DEFAULT_CONNECTION_TIMEOUT = 5 * 60; // 5 minutes 
  private final Vertx vertx;
  private final PersonRepository service;
  private final long timerID;
  private long lastAccessed;
  private final long timeoutSeconds;
  private final boolean includeDebugInfo;

  public PersonRepositoryVertxProxyHandler(Vertx vertx, PersonRepository service){
    this(vertx, service, DEFAULT_CONNECTION_TIMEOUT);
  }

  public PersonRepositoryVertxProxyHandler(Vertx vertx, PersonRepository service, long timeoutInSecond){
    this(vertx, service, true, timeoutInSecond);
  }

  public PersonRepositoryVertxProxyHandler(Vertx vertx, PersonRepository service, boolean topLevel, long timeoutInSecond){
    this(vertx, service, true, timeoutInSecond, false);
  }

  public PersonRepositoryVertxProxyHandler(Vertx vertx, PersonRepository service, boolean topLevel, long timeoutSeconds, boolean includeDebugInfo) {
      this.vertx = vertx;
      this.service = service;
      this.includeDebugInfo = includeDebugInfo;
      this.timeoutSeconds = timeoutSeconds;
      try {
        this.vertx.eventBus().registerDefaultCodec(ServiceException.class,
            new ServiceExceptionMessageCodec());
      } catch (IllegalStateException ex) {}
      if (timeoutSeconds != -1 && !topLevel) {
        long period = timeoutSeconds * 1000 / 2;
        if (period > 10000) {
          period = 10000;
        }
        this.timerID = vertx.setPeriodic(period, this::checkTimedOut);
      } else {
        this.timerID = -1;
      }
      accessed();
    }


  private void checkTimedOut(long id) {
    long now = System.nanoTime();
    if (now - lastAccessed > timeoutSeconds * 1000000000) {
      close();
    }
  }

    @Override
    public void close() {
      if (timerID != -1) {
        vertx.cancelTimer(timerID);
      }
      super.close();
    }

    private void accessed() {
      this.lastAccessed = System.nanoTime();
    }

  public void handle(Message<JsonObject> msg) {
    try{
      JsonObject json = msg.body();
      String action = msg.headers().get("action");
      if (action == null) throw new IllegalStateException("action not specified");
      accessed();
      switch (action) {
        case "findAll": {
          service.findAll(res -> {
                        if (res.failed()) {
                          HelperUtils.manageFailure(msg, res.cause(), includeDebugInfo);
                        } else {
                          msg.reply(new JsonArray(res.result().stream().map(v -> v != null ? v.toJson() : null).collect(Collectors.toList())));
                        }
                     });
          break;
        }
        case "findById": {
          service.findById((java.lang.String)json.getValue("id"),
                        res -> {
                        if (res.failed()) {
                          HelperUtils.manageFailure(msg, res.cause(), includeDebugInfo);
                        } else {
                          msg.reply(res.result() != null ? res.result().toJson() : null);
                        }
                     });
          break;
        }
        case "save": {
          service.save(json.getJsonObject("person") != null ? new com.xabe.vertx.hibernate.repository.data.Person((JsonObject)json.getJsonObject("person")) : null,
                        HelperUtils.createHandler(msg, includeDebugInfo));
          break;
        }
        case "update": {
          service.update(json.getJsonObject("person") != null ? new com.xabe.vertx.hibernate.repository.data.Person((JsonObject)json.getJsonObject("person")) : null,
                        HelperUtils.createHandler(msg, includeDebugInfo));
          break;
        }
        case "deleteAll": {
          service.deleteAll(HelperUtils.createHandler(msg, includeDebugInfo));
          break;
        }
        case "deleteById": {
          service.deleteById((java.lang.String)json.getValue("id"),
                        HelperUtils.createHandler(msg, includeDebugInfo));
          break;
        }
        default: throw new IllegalStateException("Invalid action: " + action);
      }
    } catch (Throwable t) {
      if (includeDebugInfo) msg.reply(new ServiceException(500, t.getMessage(), HelperUtils.generateDebugInfo(t)));
      else msg.reply(new ServiceException(500, t.getMessage()));
      throw t;
    }
  }
}