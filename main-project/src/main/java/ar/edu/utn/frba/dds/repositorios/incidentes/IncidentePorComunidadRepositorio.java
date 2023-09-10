package ar.edu.utn.frba.dds.repositorios.incidentes;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class IncidentePorComunidadRepositorio {

  private final EntityManager entityManager;

  public IncidentePorComunidadRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(IncidentePorComunidad incidentePorComunidad) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(incidentePorComunidad);
    transaction.commit();
  }

  public IncidentePorComunidad buscarPorId(int id) {
    return entityManager.find(IncidentePorComunidad.class, id);
  }

  public void actualizar(IncidentePorComunidad incidentePorComunidad) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(incidentePorComunidad);
    transaction.commit();
  }

  public void eliminar(IncidentePorComunidad incidentePorComunidad) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(incidentePorComunidad);
    transaction.commit();
  }

  public List<IncidentePorComunidad> buscarTodos() {
    TypedQuery<IncidentePorComunidad> query = entityManager.createQuery("FROM " + IncidentePorComunidad.class.getName(), IncidentePorComunidad.class);
    return query.getResultList();
  }
}