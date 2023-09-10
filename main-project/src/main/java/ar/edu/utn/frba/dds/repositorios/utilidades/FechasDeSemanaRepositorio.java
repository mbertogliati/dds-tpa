package ar.edu.utn.frba.dds.repositorios.utilidades;

import ar.edu.utn.frba.dds.domain.utilidades.FechasDeSemana;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class FechasDeSemanaRepositorio {

  private final EntityManager entityManager;

  public FechasDeSemanaRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(FechasDeSemana fechasDeSemana) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(fechasDeSemana);
    transaction.commit();
  }

  public FechasDeSemana buscarPorId(int id) {
    return entityManager.find(FechasDeSemana.class, id);
  }

  public void actualizar(FechasDeSemana fechasDeSemana) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(fechasDeSemana);
    transaction.commit();
  }

  public void eliminar(FechasDeSemana fechasDeSemana) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(fechasDeSemana);
    transaction.commit();
  }

  public List<FechasDeSemana> buscarTodas() {
    TypedQuery<FechasDeSemana> query = entityManager.createQuery("FROM " + FechasDeSemana.class.getName(), FechasDeSemana.class);
    return query.getResultList();
  }
}