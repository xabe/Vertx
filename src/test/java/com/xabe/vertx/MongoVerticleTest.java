package com.xabe.vertx;

import java.io.IOException;
import java.util.List;

import com.xabe.vertx.mongo.MongoVerticle;
import com.xabe.vertx.mongo.repository.CustomerRepository;
import com.xabe.vertx.mongo.repository.data.Account;
import com.xabe.vertx.mongo.repository.data.Customer;
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
public class MongoVerticleTest {

  private CustomerRepository customerRepository;

  private String id;

  @BeforeEach
  @DisplayName("Deploy a verticle")
  void prepare(final Vertx vertx, final VertxTestContext testContext) throws IOException {
    final DeploymentOptions options = new DeploymentOptions();
    vertx.deployVerticle(new MongoVerticle(), options, testContext.succeedingThenComplete());
    this.customerRepository = CustomerRepository.createProxy(vertx, MongoVerticle.REPOSITORY_ADDRESS);
  }

  @Test
  @DisplayName("Save customer")
  @Order(1)
  void save(final Vertx vertx, final VertxTestContext testContext) {
    final Customer customer = Customer.builder().name("name").age(18).build();
    this.customerRepository.save(customer, b1 -> {
      if (b1.failed()) {
        System.err.println("Cannot save!");
        testContext.failNow(b1.cause());
        return;
      }
      this.id = b1.result();
      System.out.println("Uuid " + this.id);
      testContext.completeNow();
    });
  }

  @Test
  @DisplayName("Get all customers")
  @Order(2)
  void getAll(final Vertx vertx, final VertxTestContext testContext) {
    this.customerRepository.findAll(b1 -> {
      if (b1.failed()) {
        System.err.println("Cannot get all!");
        testContext.failNow(b1.cause());
        return;
      }
      final List<Customer> result = b1.result();
      System.out.println("Customer : " + result);
      testContext.completeNow();
    });
  }

  @Test
  @DisplayName("Update customer")
  @Order(3)
  void update(final Vertx vertx, final VertxTestContext testContext) {
    final Customer customer =
        Customer.builder().id(this.id).name("nameUpdate").age(1).accounts(List.of(Account.of("1", "1", 2, "customerId"))).build();
    this.customerRepository.update(customer, b1 -> {
      if (b1.failed()) {
        System.err.println("Cannot update!");
        testContext.failNow(b1.cause());
        return;
      }
      final Long result = b1.result();
      System.out.println("Customer update : " + result);
      testContext.completeNow();
    });
  }

  @Test
  @DisplayName("Get id customer")
  @Order(4)
  void getId(final Vertx vertx, final VertxTestContext testContext) {
    this.customerRepository.findById(this.id, b1 -> {
      if (b1.failed()) {
        System.err.println("Cannot get my post!");
        testContext.failNow(b1.cause());
        return;
      }
      final Customer result = b1.result();
      System.out.println("Customer with id : " + this.id + " : " + result);
      testContext.completeNow();
    });
  }

}
