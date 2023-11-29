package ar.edu.utn.frba.dds.repositorios.entidades;

import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EntidadRepositorio implements WithSimplePersistenceUnit {

  public void guardar(Entidad entidad) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().persist(entidad);
    transaction.commit();
  }

  public Entidad buscarPorId(int id) {
    return entityManager().find(Entidad.class, id);
  }

  public void actualizar(Entidad entidad) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().merge(entidad);
    transaction.commit();
  }

  public void eliminar(Entidad entidad) {
    entidad.setActivo(false);
    this.actualizar(entidad);
    //entityManager().clear();
  }

  public List<Entidad> buscarTodas() {
    return entityManager().createQuery(
            "SELECT e FROM " + Entidad.class.getName() + " e WHERE e.activo=TRUE", Entidad.class)
        .getResultList();
  }

  public List<Entidad> buscarPorLocalidad(String idLocalidad) {
    return entityManager().createQuery(
        "SELECT e FROM "+ Entidad.class.getName() +" e WHERE e.ubicacion.metadato.localidad.id = :idBuscado AND e.activo = true",Entidad.class)
        .setParameter("idBuscado", Integer.parseInt(idLocalidad))
        .getResultList();
  }

}