package ar.edu.utn.frba.dds.repositorios;

import javax.persistence.EntityManager;

@FunctionalInterface
public interface DatabaseOperation<T> {
  T execute(EntityManager entityManager);
}
