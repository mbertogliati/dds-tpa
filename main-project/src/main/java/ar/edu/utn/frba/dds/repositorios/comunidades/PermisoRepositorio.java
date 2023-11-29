package ar.edu.utn.frba.dds.repositorios.comunidades;

import ar.edu.utn.frba.dds.modelos.comunidades.Permiso;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class PermisoRepositorio implements WithSimplePersistenceUnit {

  public void guardar(Permiso permiso) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().persist(permiso);
    transaction.commit();
  }

  public Permiso buscarPorId(int id) {
    return entityManager().find(Permiso.class, id);
  }

  public void actualizar(Permiso permiso) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().merge(permiso);
    transaction.commit();
  }

  public void eliminar(Permiso permiso) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().remove(permiso);
    transaction.commit();
  }

  public List<Permiso> buscarTodos() {
    TypedQuery<Permiso> query = entityManager().createQuery("FROM " + Permiso.class.getName(), Permiso.class);
    return query.getResultList();
  }
}