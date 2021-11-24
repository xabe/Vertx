package com.xabe.vertx.mongo.repository.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor(staticName = "of")
public class Account {

  private String id;

  private String number;

  private int balance;

  private String customerId;

}
