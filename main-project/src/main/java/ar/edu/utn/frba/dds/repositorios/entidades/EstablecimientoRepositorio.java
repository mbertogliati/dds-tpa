package ar.edu.utn.frba.dds.repositorios.entidades;

import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EstablecimientoRepositorio {

  private final EntityManager entityManager;

  public EstablecimientoRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Establecimiento establecimiento) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(establecimiento);
    transaction.commit();
  }

  public Establecimiento buscarPorId(int id) {
    return entityManager.find(Establecimiento.class, id);
  }

  public void actualizar(Establecimiento establecimiento) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(establecimiento);
    transaction.commit();
  }

  public void eliminar(Establecimiento establecimiento) {
    establecimiento.setActivo(false);
    this.actualizar(establecimiento);
    //entityManager.clear();
  }
  
  public List<Establecimiento> buscarTodos() {
    return entityManager.createQuery(
            "SELECT e FROM " + Establecimiento.class.getName() + " e WHERE e.activo=TRUE", Establecimiento.class)
        .getResultList();
  }

  public List<Establecimiento> buscarPorEntidad(String idEntidad) {
    return entityManager.createQuery(
            "SELECT e FROM "+ Establecimiento.class.getName() +" e WHERE e.entidad.id = :idBuscado AND e.activo = true",Establecimiento.class)
        .setParameter("idBuscado", Integer.parseInt(idEntidad))
        .getResultList();
  }
}