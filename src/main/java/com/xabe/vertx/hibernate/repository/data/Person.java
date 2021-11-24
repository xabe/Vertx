package com.xabe.vertx.hibernate.repository.data;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
@Entity
@Table(name = "perons")
@DataObject
public class Person {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private UUID id;

  @Column(unique = true)
  private String name;

  @Column(nullable = false)
  private Long price;

  @Builder.Default
  @Column(name = "created_at")
  @CreationTimestamp
  LocalDateTime createdAt = LocalDateTime.now();

  public Person(final JsonObject json) {
    this.getOptional(() -> json.getString("id")).map(UUID::fromString).ifPresent(this::setId);
    this.name = json.getString("name");
    this.price = json.getLong("price");
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
