package ar.edu.utn.frba.dds.repositorios.comunidades;

import ar.edu.utn.frba.dds.domain.comunidades.Rol;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class RolRepositorio {

  private final EntityManager entityManager;

  public RolRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Rol rol) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(rol);
    transaction.commit();
  }

  public Rol buscarPorId(int id) {
    return entityManager.find(Rol.class, id);
  }

  public void actualizar(Rol rol) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(rol);
    transaction.commit();
  }

  public void eliminar(Rol rol) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(rol);
    transaction.commit();
  }

  public List<Rol> buscarTodos() {
    TypedQuery<Rol> query = entityManager.createQuery("FROM " + Rol.class.getName(), Rol.class);
    return query.getResultList();
  }
}
