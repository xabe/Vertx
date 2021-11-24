package com.xabe.vertx;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.xabe.vertx.hibernate.HibernateVerticle;
import com.xabe.vertx.hibernate.repository.PersonRepository;
import com.xabe.vertx.hibernate.repository.data.Person;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class HibernateVerticleTest {

  private PersonRepository personRepository;

  private String id;

  @BeforeEach
  @DisplayName("Deploy a verticle")
  void prepare(final Vertx vertx, final VertxTestContext testContext) throws IOException {
    final DeploymentOptions options = new DeploymentOptions();
    vertx.deployVerticle(new HibernateVerticle(), options, testContext.succeedingThenComplete());
    this.personRepository = PersonRepository.createProxy(vertx, HibernateVerticle.REPOSITORY_ADDRESS);
  }

  @Test
  @DisplayName("Save person")
  @Order(1)
  void save(final Vertx vertx, final VertxTestContext testContext) {
    final Person person = Person.builder().name("name").price(100L).build();
    this.personRepository.save(person, b1 -> {
      if (b1.failed()) {
        System.err.println("Cannot save person!");
        testContext.failNow(b1.cause());
        return;
      }
      this.id = b1.result();
      System.out.println("Uuid " + this.id);
      testContext.completeNow();
    });
  }

  @Test
  @DisplayName("Get all persons")
  @Order(2)
  void getAll(final Vertx vertx, final VertxTestContext testContext) {
    this.personRepository.findAll(b1 -> {
      if (b1.failed()) {
        System.err.println("Cannot get all post!");
        testContext.failNow(b1.cause());
        return;
      }
      final List<Person> result = b1.result();
      System.out.println("Persons : " + result);
      testContext.completeNow();
    });
  }

  @Test
  @DisplayName("Update person")
  @Order(3)
  void update(final Vertx vertx, final VertxTestContext testContext) {
    final Person person = Person.builder().id(UUID.fromString(this.id)).name("titleUpdate").price(1L).build();
    this.personRepository.update(person, b1 -> {
      if (b1.failed()) {
        System.err.println("Cannot get update!");
        testContext.failNow(b1.cause());
        return;
      }
      final Void result = b1.result();
      System.out.println("Pots update : " + result);
      testContext.completeNow();
    });
  }

  @Test
  @DisplayName("Get id person")
  @Order(4)
  void getId(final Vertx vertx, final VertxTestContext testContext) {
    this.personRepository.findById(this.id, b1 -> {
      if (b1.failed()) {
        System.err.println("Cannot get my post!");
        testContext.failNow(b1.cause());
        return;
      }
      final Person result = b1.result();
      System.out.println("Person with id : " + this.id + " : " + result);
      testContext.completeNow();
    });
  }

}
