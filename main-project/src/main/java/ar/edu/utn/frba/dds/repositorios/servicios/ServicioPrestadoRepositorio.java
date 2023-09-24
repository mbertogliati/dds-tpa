package ar.edu.utn.frba.dds.repositorios.servicios;

import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class ServicioPrestadoRepositorio {

  private final EntityManager entityManager;

  public ServicioPrestadoRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(ServicioPrestado servicioPrestado) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(servicioPrestado);
    transaction.commit();
  }

  public ServicioPrestado buscarPorId(int id) {
    return entityManager.find(ServicioPrestado.class, id);
  }

  public void actualizar(ServicioPrestado servicioPrestado) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(servicioPrestado);
    transaction.commit();
  }

  public void eliminar(ServicioPrestado servicioPrestado) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(servicioPrestado);
    transaction.commit();
  }

  public List<ServicioPrestado> buscarTodos() {
    TypedQuery<ServicioPrestado> query = entityManager.createQuery("FROM " + ServicioPrestado.class.getName(), ServicioPrestado.class);
    return query.getResultList();
  }
}