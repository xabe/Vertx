package com.xabe.vertx.mongo.repository;

import java.util.List;
import java.util.stream.Collectors;

import com.xabe.vertx.mongo.repository.data.Customer;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.mongo.MongoClientDeleteResult;
import io.vertx.ext.mongo.MongoClientUpdateResult;
import io.vertx.ext.mongo.UpdateOptions;

public class CustomerRepositoryImpl implements CustomerRepository {

  public static final String CUSTOMER = "customer";

  public static final String ID = "_id";

  private final MongoClient mongoClient;

  public CustomerRepositoryImpl(final MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  private List<Customer> mapperCustomer(final List<JsonObject> jsonObjects) {
    return jsonObjects.stream().map(Customer::new).collect(Collectors.toList());
  }

  @Override
  public CustomerRepository findAll(final Handler<AsyncResult<List<Customer>>> resultHandler) {
    this.mongoClient.find(CUSTOMER, new JsonObject()).map(this::mapperCustomer).onComplete(resultHandler);
    return this;
  }

  @Override
  public CustomerRepository findById(final String id, final Handler<AsyncResult<Customer>> resultHandler) {
    this.mongoClient.find(CUSTOMER, new JsonObject().put(ID, id)).map(this::mapperCustomer).map(item -> item.get(0))
        .onComplete(resultHandler);
    return this;
  }

  @Override
  public CustomerRepository save(final Customer customer, final Handler<AsyncResult<String>> resultHandler) {
    final JsonObject json = JsonObject.mapFrom(customer);
    this.mongoClient.save(CUSTOMER, json).onComplete(resultHandler);
    return this;
  }

  @Override
  public CustomerRepository update(final Customer customer, final Handler<AsyncResult<Long>> resultHandler) {
    final JsonObject query = new JsonObject().put(ID, customer.getId());
    final JsonObject update = JsonObject.mapFrom(customer);
    final UpdateOptions updateOptions = new UpdateOptions();
    updateOptions.setUpsert(true).setMulti(true);
    this.mongoClient.updateCollectionWithOptions(CUSTOMER, query, new JsonObject().put("$set", update), updateOptions)
        .map(MongoClientUpdateResult::getDocModified)
        .onComplete(resultHandler);
    return this;
  }

  @Override
  public CustomerRepository deleteAll(final Handler<AsyncResult<Long>> resultHandler) {
    this.mongoClient.removeDocuments(CUSTOMER, new JsonObject()).map(MongoClientDeleteResult::getRemovedCount).onComplete(resultHandler);
    return this;
  }

  @Override
  public CustomerRepository deleteById(final String id, final Handler<AsyncResult<Long>> resultHandler) {
    this.mongoClient.removeDocument(CUSTOMER, new JsonObject().put(ID, id)).map(MongoClientDeleteResult::getRemovedCount)
        .onComplete(resultHandler);
    return this;
  }
}
