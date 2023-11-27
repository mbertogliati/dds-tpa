package ar.edu.utn.frba.dds.repositorios.utilidades;

import ar.edu.utn.frba.dds.modelos.utilidades.CronTask;
import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class CronTaskRepositorio {
  private final EntityManager entityManager;

  public CronTaskRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(CronTask cronTask) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(cronTask);
    transaction.commit();
  }

  public List<CronTask> obtener() {
    TypedQuery<CronTask> query = entityManager.createQuery("FROM " + CronTask.class.getName() + " ORDER BY id", CronTask.class);
    return query.getResultList();
  }

  public CronTask obtenerPorId(Long id) {
    return entityManager.find(CronTask.class, id);
  }

  public void actualizar(CronTask cronTask) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(cronTask);
    transaction.commit();
  }

  public void borrar(CronTask cronTask) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(cronTask);
    transaction.commit();
  }

  public void refresh(CronTask cronTask) {
    entityManager.refresh(cronTask);
  }

  public void detach(CronTask cronTask) {
    entityManager.detach(cronTask);
  }
}
