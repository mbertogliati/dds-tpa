package ar.edu.utn.frba.dds.repositorios.comunidades;

import ar.edu.utn.frba.dds.domain.comunidades.Membresia;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class MembresiaRepositorio {

  private final EntityManager entityManager;

  public MembresiaRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Membresia membresia) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(membresia);
    transaction.commit();
  }

  public Membresia buscarPorId(int id) {
    return entityManager.find(Membresia.class, id);
  }

  public void actualizar(Membresia membresia) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(membresia);
    transaction.commit();
  }

  public void eliminar(Membresia membresia) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(membresia);
    transaction.commit();
  }

  public List<Membresia> buscarTodas() {
    TypedQuery<Membresia> query = entityManager.createQuery("FROM " + Membresia.class.getName(), Membresia.class);
    return query.getResultList();
  }
}
