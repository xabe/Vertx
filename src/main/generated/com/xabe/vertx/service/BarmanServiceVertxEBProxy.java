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

package com.xabe.vertx.service;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.Vertx;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.function.Function;
import io.vertx.serviceproxy.ServiceException;
import io.vertx.serviceproxy.ServiceExceptionMessageCodec;
import io.vertx.serviceproxy.ProxyUtils;

import com.xabe.vertx.service.Beer;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
/*
  Generated Proxy code - DO NOT EDIT
  @author Roger the Robot
*/

@SuppressWarnings({"unchecked", "rawtypes"})
public class BarmanServiceVertxEBProxy implements BarmanService {
  private Vertx _vertx;
  private String _address;
  private DeliveryOptions _options;
  private boolean closed;

  public BarmanServiceVertxEBProxy(Vertx vertx, String address) {
    this(vertx, address, null);
  }

  public BarmanServiceVertxEBProxy(Vertx vertx, String address, DeliveryOptions options) {
    this._vertx = vertx;
    this._address = address;
    this._options = options;
    try {
      this._vertx.eventBus().registerDefaultCodec(ServiceException.class, new ServiceExceptionMessageCodec());
    } catch (IllegalStateException ex) {
    }
  }

  @Override
  public void giveMeARandomBeer(String customerName, Handler<AsyncResult<Beer>> handler){
    if (closed) {
      handler.handle(Future.failedFuture(new IllegalStateException("Proxy is closed")));
      return;
    }
    JsonObject _json = new JsonObject();
    _json.put("customerName", customerName);

    DeliveryOptions _deliveryOptions = (_options != null) ? new DeliveryOptions(_options) : new DeliveryOptions();
    _deliveryOptions.addHeader("action", "giveMeARandomBeer");
    _vertx.eventBus().<JsonObject>request(_address, _json, _deliveryOptions, res -> {
      if (res.failed()) {
        handler.handle(Future.failedFuture(res.cause()));
      } else {
        handler.handle(Future.succeededFuture(res.result().body() != null ? new com.xabe.vertx.service.Beer((JsonObject)res.result().body()) : null));
      }
    });
  }
  @Override
  public void getMyBill(String customerName, Handler<AsyncResult<Integer>> handler){
    if (closed) {
      handler.handle(Future.failedFuture(new IllegalStateException("Proxy is closed")));
      return;
    }
    JsonObject _json = new JsonObject();
    _json.put("customerName", customerName);

    DeliveryOptions _deliveryOptions = (_options != null) ? new DeliveryOptions(_options) : new DeliveryOptions();
    _deliveryOptions.addHeader("action", "getMyBill");
    _vertx.eventBus().<Integer>request(_address, _json, _deliveryOptions, res -> {
      if (res.failed()) {
        handler.handle(Future.failedFuture(res.cause()));
      } else {
        handler.handle(Future.succeededFuture(res.result().body()));
      }
    });
  }
  @Override
  public void payMyBill(String customerName){
    if (closed) throw new IllegalStateException("Proxy is closed");
    JsonObject _json = new JsonObject();
    _json.put("customerName", customerName);

    DeliveryOptions _deliveryOptions = (_options != null) ? new DeliveryOptions(_options) : new DeliveryOptions();
    _deliveryOptions.addHeader("action", "payMyBill");
    _vertx.eventBus().send(_address, _json, _deliveryOptions);
  }
}
