package ar.edu.utn.frba.dds.server.jpa_extensions;

import io.github.flbulgarelli.jpa.extras.WithEntityManager;
import io.github.flbulgarelli.jpa.extras.perthread.PerThreadEntityManagerProperties;
import javax.persistence.EntityManager;

public interface WithPerThreadEntityManagerWithConfiguration extends WithEntityManager {
  default EntityManager entityManager() {
    PerThreadEntityManagerProperties properties = new PerThreadEntityManagerProperties();

    //TODO: qué convención usar?

    //hibernate naming convention
    properties.set("hibernate.connection.url",System.getenv("DB_CONNECTION_HOST"));
    properties.set("hibernate.connection.username", System.getenv("DB_CONNECTION_USERNAME"));
    properties.set("hibernate.connection.password",System.getenv("DB_CONNECTION_PASSWORD"));
    properties.set("hibernate.connection.driver_class", System.getenv("DB_CONNECTION_DRIVER_CLASS"));

    //Javax naming convention
    properties.set("javax.persistence.jdbc.url", System.getenv("DB_CONNECTION_HOST"));
    properties.set("javax.persistence.jdbc.user", System.getenv("DB_CONNECTION_USERNAME"));
    properties.set("javax.persistence.jdbc.password",System.getenv("DB_CONNECTION_PASSWORD"));
    properties.set("javax.persistence.jdbc.driver", System.getenv("DB_CONNECTION_DRIVER_CLASS"));

    properties.set("hibernate.hbm2ddl.auto", System.getenv("DB_CONNECTION_HIBERNATE_HBM2DDL_AUTO"));
    properties.set("hibernate.dialect", System.getenv("DB_CONNECTION_HIBERNATE_DIALECT"));
    properties.set("hibernate.show_sql", System.getenv("DB_CONNECTION_HIBERNATE_SHOW_SQL"));

    PerThreadEntityManagerAccessWithConfiguration entityManagerAccess = new PerThreadEntityManagerAccessWithConfiguration("simple-persistence-unit", properties);
    return entityManagerAccess.get();
  }
}