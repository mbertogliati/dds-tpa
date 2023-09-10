package ar.edu.utn.frba.dds.repositorios.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona.ListadoNotificables;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class ListadoNotificablesRepositorio {
  private EntityManager entityManager;

  public ListadoNotificablesRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardarListadoNotificables(ListadoNotificables listadoNotificables) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      entityManager.persist(listadoNotificables);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public ListadoNotificables obtenerListadoNotificablesPorId(int id) {
    return entityManager.find(ListadoNotificables.class, id);
  }

  public void actualizarListadoNotificables(ListadoNotificables listadoNotificables) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      entityManager.merge(listadoNotificables);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public void eliminarListadoNotificables(ListadoNotificables listadoNotificables) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      ListadoNotificables managedListadoNotificables = entityManager.find(ListadoNotificables.class, listadoNotificables.getId());
      entityManager.remove(managedListadoNotificables);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public List<ListadoNotificables> obtenerTodos() {
    TypedQuery<ListadoNotificables> query = entityManager.createQuery("FROM " + ListadoNotificables.class.getName(), ListadoNotificables.class);
    return query.getResultList();
  }
}
