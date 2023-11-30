package ar.edu.utn.frba.dds.repositorios.meta_datos_geo;

import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Localidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class LocalidadRepositorio implements WithSimplePersistenceUnit {

  public void guardarLocalidad(Localidad localidad) {
    EntityTransaction transaction = entityManager().getTransaction();

    try {
      transaction.begin();
      entityManager().persist(localidad);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public Localidad obtenerLocalidadPorId(String id) {
    return entityManager().find(Localidad.class, Integer.parseInt(id));
  }

  public Localidad obtenerLocalidadPorId(Integer id) {
    return entityManager().find(Localidad.class, id);
  }

  public void actualizarLocalidad(Localidad localidad) {
    EntityTransaction transaction = entityManager().getTransaction();

    try {
      transaction.begin();
      entityManager().merge(localidad);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public void eliminarLocalidad(Localidad localidad) {
    EntityTransaction transaction = entityManager().getTransaction();

    try {
      transaction.begin();
      Localidad managedLocalidad = entityManager().find(Localidad.class, localidad.getId());
      entityManager().remove(managedLocalidad);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public List<Localidad> obtenerTodas() {
    TypedQuery<Localidad> query = entityManager().createQuery("SELECT l FROM Localidad l ORDER BY l.nombre", Localidad.class);
    return query.getResultList();
  }

  public List<Localidad> buscarPorDepartamento(Integer idDepartamento) {
    return entityManager().createQuery(
            "SELECT l FROM Localidad l WHERE l.departamento.id = :idBuscado ORDER BY l.nombre", Localidad.class)
        .setParameter("idBuscado", idDepartamento)
        .getResultList();
  }

  public void refresh(Localidad localidad) {
    this.entityManager().refresh(localidad);
  }
}
