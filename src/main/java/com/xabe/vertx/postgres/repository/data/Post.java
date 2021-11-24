package com.xabe.vertx.postgres.repository.data;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@DataObject
@AllArgsConstructor(staticName = "of")
public class Post {

  UUID id;

  String title;

  String content;

  LocalDateTime createdAt;

  public Post(final JsonObject json) {
    this.getOptional(() -> json.getString("id")).map(UUID::fromString).ifPresent(this::setId);
    this.title = json.getString("title");
    this.content = json.getString("content");
    this.getOptional(() -> json.getString("createdAt")).map(item -> LocalDateTime.parse(item, ISO_LOCAL_DATE_TIME))
        .ifPresent(this::setCreatedAt);
  }

  public JsonObject toJson() {
    final JsonObject jsonObject = JsonObject.mapFrom(this);
    if (Objects.nonNull(this.createdAt)) {
      jsonObject.put("createdAt", this.createdAt.format(ISO_LOCAL_DATE_TIME));
    }
    return jsonObject;
  }

  private <T> Optional<T> getOptional(final Supplier<T> supplier) {
    return Optional.ofNullable(supplier.get());
  }
}
