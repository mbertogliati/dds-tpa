package ar.edu.utn.frba.dds.repositorios.rankings;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.rankings.Ranking;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

  public List<Ranking> obtenerTodos(Usuario usuario) {
    return this.obtenerTodos().stream().map(r -> r.filtradoPara(usuario)).filter(r -> !r.estaVacio()).toList();
  }

  public List<Ranking> buscarConFechaCreacionPosteriorA(String fecha) {
    return entityManager.createQuery(
            "SELECT r FROM Ranking r WHERE r.fechaHoraCreacion > :fechaCreacion", Ranking.class)
        .setParameter("fechaCreacion", LocalDate.parse(fecha).atStartOfDay())
        .getResultList();
  }

  public List<Ranking> buscarConFechaCreacionPosteriorA(String fecha, Usuario usuario) {
    List<Ranking> lista = buscarConFechaCreacionPosteriorA(fecha);
    return lista.stream().map(r -> r.filtradoPara(usuario)).filter(r -> !r.estaVacio()).toList();
  }
}
