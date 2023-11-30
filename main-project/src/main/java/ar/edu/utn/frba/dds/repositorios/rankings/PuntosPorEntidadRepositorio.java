package ar.edu.utn.frba.dds.repositorios.rankings;

import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.modelos.rankings.PuntosPorEntidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class PuntosPorEntidadRepositorio implements WithSimplePersistenceUnit {

  public void guardar(PuntosPorEntidad puntosPorEntidad) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().persist(puntosPorEntidad);
    transaction.commit();
  }

  public PuntosPorEntidad buscarPorId(int id) {
    return entityManager().find(PuntosPorEntidad.class, id);
  }

  public void actualizar(PuntosPorEntidad puntosPorEntidad) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().merge(puntosPorEntidad);
    transaction.commit();
  }

  public void eliminar(PuntosPorEntidad puntosPorEntidad) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().remove(puntosPorEntidad);
    transaction.commit();
  }

  public List<PuntosPorEntidad> buscarTodos() {
    TypedQuery<PuntosPorEntidad> query = entityManager().createQuery("FROM " + PuntosPorEntidad.class.getName(), PuntosPorEntidad.class);
    return query.getResultList();
  }

  public void refresh(PuntosPorEntidad puntosPorEntidad) {
    this.entityManager().refresh(puntosPorEntidad);
  }
}