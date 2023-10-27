package ar.edu.utn.frba.dds.controllers.utils;

import ar.edu.utn.frba.dds.server.jpa_extensions.WithPerThreadEntityManagerWithConfiguration;
import io.github.flbulgarelli.jpa.extras.perthread.PerThreadEntityManagerProperties;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

public class CreadorEntityManager implements WithPerThreadEntityManagerWithConfiguration {
  public CreadorEntityManager(){}
  public EntityManager entityManagerCreado() {
    return entityManager();
  }
}
