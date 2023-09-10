package ar.edu.utn.frba.dds.repositorios.meta_datos_geo;

import ar.edu.utn.frba.dds.domain.meta_datos_geo.Localidad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class LocalidadRepositorio {
  private EntityManager entityManager;

  public LocalidadRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardarLocalidad(Localidad localidad) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      entityManager.persist(localidad);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public Localidad obtenerLocalidadPorId(String id) {
    return entityManager.find(Localidad.class, id);
  }

  public void actualizarLocalidad(Localidad localidad) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      entityManager.merge(localidad);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public void eliminarLocalidad(Localidad localidad) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      Localidad managedLocalidad = entityManager.find(Localidad.class, localidad.getId());
      entityManager.remove(managedLocalidad);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public List<Localidad> obtenerTodas() {
    TypedQuery<Localidad> query = entityManager.createQuery("FROM " + Localidad.class.getName(), Localidad.class);
    return query.getResultList();
  }
}
