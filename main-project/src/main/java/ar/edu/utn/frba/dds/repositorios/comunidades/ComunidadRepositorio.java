package ar.edu.utn.frba.dds.repositorios.comunidades;
import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
    comunidad.setActivo(false);
    this.actualizarComunidad(comunidad);
    //entityManager.clear();
  }

  public List<Comunidad> obtenerTodas() {
    return entityManager.createQuery(
            "FROM " + Comunidad.class.getName(), Comunidad.class)
        .getResultList();
  }
}

