package com.xabe.vertx;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.xabe.vertx.postgres.PostGreSqlVerticle;
import com.xabe.vertx.postgres.repository.PostRepository;
import com.xabe.vertx.postgres.repository.data.Post;
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
public class PostGreSqlVerticleTest {

  private PostRepository postRepository;

  private String id;

  @BeforeEach
  @DisplayName("Deploy a verticle")
  void prepare(final Vertx vertx, final VertxTestContext testContext) throws IOException {
    final DeploymentOptions options = new DeploymentOptions();
    vertx.deployVerticle(new PostGreSqlVerticle(), options, testContext.succeedingThenComplete());
    this.postRepository = PostRepository.createProxy(vertx, PostGreSqlVerticle.REPOSITORY_ADDRESS);
  }

  @Test
  @DisplayName("Save posts")
  @Order(1)
  void save(final Vertx vertx, final VertxTestContext testContext) {
    final Post post = Post.builder().title("title").content("content").build();
    this.postRepository.save(post, b1 -> {
      if (b1.failed()) {
        System.err.println("Cannot save post!");
        testContext.failNow(b1.cause());
        return;
      }
      this.id = b1.result();
      System.out.println("Uuid " + this.id);
      testContext.completeNow();
    });
  }

  @Test
  @DisplayName("Get all posts")
  @Order(2)
  void getAll(final Vertx vertx, final VertxTestContext testContext) {
    this.postRepository.findAll(b1 -> {
      if (b1.failed()) {
        System.err.println("Cannot get all post!");
        testContext.failNow(b1.cause());
        return;
      }
      final List<Post> result = b1.result();
      System.out.println("Pots : " + result);
      testContext.completeNow();
    });
  }

  @Test
  @DisplayName("Update posts")
  @Order(3)
  void update(final Vertx vertx, final VertxTestContext testContext) {
    final Post post = Post.builder().id(UUID.fromString(this.id)).title("titleUpdate").content("contentUpdate").build();
    this.postRepository.update(post, b1 -> {
      if (b1.failed()) {
        System.err.println("Cannot get update!");
        testContext.failNow(b1.cause());
        return;
      }
      final Integer result = b1.result();
      System.out.println("Pots update : " + result);
      testContext.completeNow();
    });
  }

  @Test
  @DisplayName("Get id posts")
  @Order(4)
  void getId(final Vertx vertx, final VertxTestContext testContext) {
    this.postRepository.findById(this.id, b1 -> {
      if (b1.failed()) {
        System.err.println("Cannot get my post!");
        testContext.failNow(b1.cause());
        return;
      }
      final Post result = b1.result();
      System.out.println("Pots with id : " + this.id + " : " + result);
      testContext.completeNow();
    });
  }

}
