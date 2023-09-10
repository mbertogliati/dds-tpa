package ar.edu.utn.frba.dds.repositorios.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona.NotificableConFecha;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class NotificableConFechaRepositorio {
  private EntityManager entityManager;

  public NotificableConFechaRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardarNotificableConFecha(NotificableConFecha notificableConFecha) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      entityManager.persist(notificableConFecha);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public NotificableConFecha obtenerNotificableConFechaPorId(int id) {
    return entityManager.find(NotificableConFecha.class, id);
  }

  public void actualizarNotificableConFecha(NotificableConFecha notificableConFecha) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      entityManager.merge(notificableConFecha);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public void eliminarNotificableConFecha(NotificableConFecha notificableConFecha) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      NotificableConFecha managedNotificableConFecha = entityManager.find(NotificableConFecha.class, notificableConFecha.getId());
      entityManager.remove(managedNotificableConFecha);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public List<NotificableConFecha> obtenerTodos() {
    TypedQuery<NotificableConFecha> query = entityManager.createQuery("FROM " + NotificableConFecha.class.getName(), NotificableConFecha.class);
    return query.getResultList();
  }
}
