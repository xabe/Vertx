package com.xabe.vertx.mongo.repository.data;

import java.util.List;
import java.util.Objects;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@DataObject
@AllArgsConstructor(staticName = "of")
public class Customer {

  private String id;

  private String name;

  private int age;

  private List<Account> accounts;

  public Customer(final JsonObject json) {
    this.id = Objects.isNull(json.getString("id")) ? json.getString("_id") : json.getString("id");
    this.name = json.getString("name");
    this.age = json.getInteger("age");
  }

  public JsonObject toJson() {
    return JsonObject.mapFrom(this);
  }

}
