package ar.edu.utn.frba.dds.repositorios.entidades;

import ar.edu.utn.frba.dds.domain.entidades.Denominacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class DenominacionRepositorio {

  private final EntityManager entityManager;

  public DenominacionRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Denominacion denominacion) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(denominacion);
    transaction.commit();
  }

  public Denominacion buscarPorId(int id) {
    return entityManager.find(Denominacion.class, id);
  }

  public void actualizar(Denominacion denominacion) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(denominacion);
    transaction.commit();
  }

  public void eliminar(Denominacion denominacion) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(denominacion);
    transaction.commit();
  }

  public List<Denominacion> buscarTodas() {
    TypedQuery<Denominacion> query = entityManager.createQuery("FROM " + Denominacion.class.getName(), Denominacion.class);
    return query.getResultList();
  }
}