package ar.edu.utn.frba.dds.repositorios.entidades;

import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

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
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(establecimiento);
    transaction.commit();
  }

  public List<Establecimiento> buscarTodos() {
    TypedQuery<Establecimiento> query = entityManager.createQuery("FROM " + Establecimiento.class.getName(), Establecimiento.class);
    return query.getResultList();
  }

  public List<Establecimiento> buscarPorEntidad(String idEntidad) {
    return (List<Establecimiento>) entityManager.createQuery(
            "SELECT e FROM Establecimiento e WHERE e.entidad.id = :idBuscado")
        .setParameter("idBuscado", Integer.parseInt(idEntidad))
        .getResultList();
  }
}