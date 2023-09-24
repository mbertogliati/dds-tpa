package ar.edu.utn.frba.dds.repositorios.rankings;

import ar.edu.utn.frba.dds.modelos.rankings.Ranking;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class RankingRepositorio {
  private EntityManager entityManager;

  public RankingRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardarRanking(Ranking ranking) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      entityManager.persist(ranking);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public Ranking obtenerRankingPorId(int id) {
    return entityManager.find(Ranking.class, id);
  }

  public void actualizarRanking(Ranking ranking) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      entityManager.merge(ranking);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public void eliminarRanking(Ranking ranking) {
    EntityTransaction transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      Ranking managedRanking = entityManager.find(Ranking.class, ranking.getId());
      entityManager.remove(managedRanking);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public List<Ranking> obtenerTodos() {
    TypedQuery<Ranking> query = entityManager.createQuery("FROM " + Ranking.class.getName(), Ranking.class);
    return query.getResultList();
  }
}
