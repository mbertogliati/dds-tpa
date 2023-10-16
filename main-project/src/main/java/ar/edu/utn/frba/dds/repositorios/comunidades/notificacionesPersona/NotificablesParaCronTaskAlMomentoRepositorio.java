package ar.edu.utn.frba.dds.repositorios.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona.NotificableConFecha;
import ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona.NotificablesParaCronTaskAlMomento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class NotificablesParaCronTaskAlMomentoRepositorio {
  private EntityManager entityManager;

  public NotificablesParaCronTaskAlMomentoRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(NotificablesParaCronTaskAlMomento notificable) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      entityManager.persist(notificable);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public NotificablesParaCronTaskAlMomento obtenerPorId(int id) {
    return entityManager.find(NotificablesParaCronTaskAlMomento.class, id);
  }

  public void actualizar(NotificablesParaCronTaskAlMomento notificable) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      entityManager.merge(notificable);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public void eliminar(NotificablesParaCronTaskAlMomento notificable) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      NotificablesParaCronTaskAlMomento managedNotificableConFecha = entityManager.find(NotificablesParaCronTaskAlMomento.class, notificable.getId());
      entityManager.remove(managedNotificableConFecha);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public List<NotificablesParaCronTaskAlMomento> obtenerTodos() {
    TypedQuery<NotificablesParaCronTaskAlMomento> query = entityManager.createQuery("FROM " + NotificablesParaCronTaskAlMomento.class.getName(), NotificablesParaCronTaskAlMomento.class);
    return query.getResultList();
  }
}
