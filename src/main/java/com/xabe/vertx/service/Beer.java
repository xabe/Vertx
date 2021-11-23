package com.xabe.vertx.service;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@DataObject
//@DataObject(generateConverter = true)
@AllArgsConstructor
public class Beer {

  private String name;

  private String style;

  private int price;

  public Beer(final JsonObject json) {
    this.name = json.getString("name");
    this.style = json.getString("style");
    this.price = json.getInteger("price");
  }

  public JsonObject toJson() {
    return JsonObject.mapFrom(this);
  }
}
