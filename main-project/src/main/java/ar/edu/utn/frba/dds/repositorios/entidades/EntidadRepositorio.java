package ar.edu.utn.frba.dds.repositorios.entidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EntidadRepositorio {

  private final EntityManager entityManager;

  public EntidadRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Entidad entidad) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(entidad);
    transaction.commit();
  }

  public Entidad buscarPorId(int id) {
    return entityManager.find(Entidad.class, id);
  }

  public void actualizar(Entidad entidad) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(entidad);
    transaction.commit();
  }

  public void eliminar(Entidad entidad) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(entidad);
    transaction.commit();
  }

  public List<Entidad> buscarTodos() {
    return entityManager.createQuery("FROM " + Entidad.class.getName(), Entidad.class)
        .getResultList();
  }
}