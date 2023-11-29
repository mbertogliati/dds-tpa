package ar.edu.utn.frba.dds.repositorios.utilidades;

import ar.edu.utn.frba.dds.modelos.utilidades.FechasDeSemana;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class FechasDeSemanaRepositorio implements WithSimplePersistenceUnit {

  public void guardar(FechasDeSemana fechasDeSemana) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().persist(fechasDeSemana);
    transaction.commit();
  }

  public FechasDeSemana buscarPorId(int id) {
    return entityManager().find(FechasDeSemana.class, id);
  }

  public void actualizar(FechasDeSemana fechasDeSemana) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().merge(fechasDeSemana);
    transaction.commit();
  }

  public void eliminar(FechasDeSemana fechasDeSemana) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().remove(fechasDeSemana);
    transaction.commit();
  }

  public List<FechasDeSemana> buscarTodas() {
    TypedQuery<FechasDeSemana> query = entityManager().createQuery("FROM " + FechasDeSemana.class.getName(), FechasDeSemana.class);
    return query.getResultList();
  }
}