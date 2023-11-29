package ar.edu.utn.frba.dds.server.jpa_extensions;

import io.github.flbulgarelli.jpa.extras.WithEntityManager;
import io.github.flbulgarelli.jpa.extras.perthread.PerThreadEntityManagerProperties;
import javax.persistence.EntityManager;

public interface WithPerThreadEntityManagerWithConfiguration extends WithEntityManager {
  EntityManager entityManager();
}