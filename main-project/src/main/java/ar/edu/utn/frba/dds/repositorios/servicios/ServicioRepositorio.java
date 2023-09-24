package ar.edu.utn.frba.dds.repositorios.servicios;

import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class ServicioRepositorio {

  private final EntityManager entityManager;

  public ServicioRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Servicio servicio) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(servicio);
    transaction.commit();
  }

  public Servicio buscarPorId(int id) {
    return entityManager.find(Servicio.class, id);
  }

  public void actualizar(Servicio servicio) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(servicio);
    transaction.commit();
  }

  public void eliminar(Servicio servicio) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(servicio);
    transaction.commit();
  }

  public List<Servicio> buscarTodos() {
    TypedQuery<Servicio> query = entityManager.createQuery("FROM " + Servicio.class.getName(), Servicio.class);
    return query.getResultList();
  }
}