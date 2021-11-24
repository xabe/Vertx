package com.xabe.vertx.hibernate;

import java.util.Map;

import com.xabe.vertx.hibernate.repository.PersonRepository;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ServiceBinder;
import javax.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(HibernateVerticle.class);

  public static final String REPOSITORY_ADDRESS = "hibernate.repository";

  private Mutiny.SessionFactory emf;

  @Override
  public Uni<Void> asyncStart() {

    final Uni<Void> startHibernate = Uni.createFrom().deferred(() -> {
      final var pgPort = this.config().getInteger("pgPort", 5432);
      final var props = Map.of("javax.persistence.jdbc.url", "jdbc:postgresql://localhost:" + pgPort + "/blogdb");

      this.emf = Persistence
          .createEntityManagerFactory("pg-demo", props)
          .unwrap(Mutiny.SessionFactory.class);
      final PersonRepository personRepository = PersonRepository.create(this.emf);
      return Uni.createFrom().emitter(emitter -> {
        new ServiceBinder(this.vertx.getDelegate())
            .setAddress(REPOSITORY_ADDRESS)
            .register(PersonRepository.class, personRepository).exceptionHandler(throwable -> {
              LOGGER.error("Failed to establish PostgreSQL database service", throwable);
              emitter.fail(throwable);
            })
            .completionHandler(res -> {
              LOGGER.info("PostgreSQL database service is successfully established in person");
              emitter.complete(null);
            });
      });
    });
    return this.vertx.executeBlocking(startHibernate)
        .onItem().invoke(() -> LOGGER.info("✅ Hibernate Reactive is ready"));
  }
}
