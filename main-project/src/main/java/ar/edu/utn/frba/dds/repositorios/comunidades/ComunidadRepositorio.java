package ar.edu.utn.frba.dds.repositorios.comunidades;
import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class ComunidadRepositorio {
  private EntityManager entityManager;

  public ComunidadRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardarComunidad(Comunidad comunidad) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      entityManager.persist(comunidad);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public Comunidad obtenerComunidadPorId(int id) {
    return entityManager.find(Comunidad.class, id);
  }

  public void actualizarComunidad(Comunidad comunidad) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      entityManager.merge(comunidad);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public void eliminarComunidad(Comunidad comunidad) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      Comunidad managedComunidad = entityManager.find(Comunidad.class, comunidad.getId());
      entityManager.remove(managedComunidad);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public List<Comunidad> obtenerTodas() {
    TypedQuery<Comunidad> query = entityManager.createQuery("FROM " + Comunidad.class.getName(), Comunidad.class);
    return query.getResultList();
  }
}

