package ar.edu.utn.frba.dds.repositorios.rankings;

import ar.edu.utn.frba.dds.domain.rankings.PuntosPorEntidad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class PuntosPorEntidadRepositorio {

  private final EntityManager entityManager;

  public PuntosPorEntidadRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(PuntosPorEntidad puntosPorEntidad) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(puntosPorEntidad);
    transaction.commit();
  }

  public PuntosPorEntidad buscarPorId(int id) {
    return entityManager.find(PuntosPorEntidad.class, id);
  }

  public void actualizar(PuntosPorEntidad puntosPorEntidad) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(puntosPorEntidad);
    transaction.commit();
  }

  public void eliminar(PuntosPorEntidad puntosPorEntidad) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(puntosPorEntidad);
    transaction.commit();
  }

  public List<PuntosPorEntidad> buscarTodos() {
    TypedQuery<PuntosPorEntidad> query = entityManager.createQuery("FROM " + PuntosPorEntidad.class.getName(), PuntosPorEntidad.class);
    return query.getResultList();
  }
}