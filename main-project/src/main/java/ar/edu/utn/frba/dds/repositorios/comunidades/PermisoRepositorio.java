package ar.edu.utn.frba.dds.repositorios.comunidades;
import ar.edu.utn.frba.dds.domain.comunidades.Permiso;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class PermisoRepositorio {

  private final EntityManager entityManager;

  public PermisoRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Permiso permiso) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(permiso);
    transaction.commit();
  }

  public Permiso buscarPorId(int id) {
    return entityManager.find(Permiso.class, id);
  }

  public void actualizar(Permiso permiso) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(permiso);
    transaction.commit();
  }

  public void eliminar(Permiso permiso) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(permiso);
    transaction.commit();
  }

  public List<Permiso> buscarTodos() {
    TypedQuery<Permiso> query = entityManager.createQuery("FROM " + Permiso.class.getName(), Permiso.class);
    return query.getResultList();
  }
}