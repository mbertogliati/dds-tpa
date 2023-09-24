package ar.edu.utn.frba.dds.repositorios.incidentes;

import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class IncidenteRepositorio {

  private final EntityManager entityManager;

  public IncidenteRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Incidente incidente) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(incidente);
    transaction.commit();
  }

  public Incidente buscarPorId(int id) {
    return entityManager.find(Incidente.class, id);
  }

  public void actualizar(Incidente incidente) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(incidente);
    transaction.commit();
  }

  public void eliminar(Incidente incidente) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(incidente);
    transaction.commit();
  }

  public List<Incidente> buscarTodos() {
    TypedQuery<Incidente> query = entityManager.createQuery("FROM " + Incidente.class.getName(), Incidente.class);
    return query.getResultList();
  }
}