package ar.edu.utn.frba.dds.repositorios.comunidades;
import ar.edu.utn.frba.dds.domain.comunidades.Interes;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class InteresRepositorio {
  private EntityManager entityManager;

  public InteresRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardarInteres(Interes interes) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      entityManager.persist(interes);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public Interes obtenerInteresPorId(int id) {
    return entityManager.find(Interes.class, id);
  }

  public void actualizarInteres(Interes interes) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      entityManager.merge(interes);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public void eliminarInteres(Interes interes) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      Interes managedInteres = entityManager.find(Interes.class, interes.getId());
      entityManager.remove(managedInteres);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public List<Interes> obtenerTodos() {
    TypedQuery<Interes> query = entityManager.createQuery("FROM " + Interes.class.getName(), Interes.class);
    return query.getResultList();
  }
}
