package ar.edu.utn.frba.dds.repositorios.entidades;

import ar.edu.utn.frba.dds.modelos.entidades.Denominacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class DenominacionRepositorio implements WithSimplePersistenceUnit {

  public void guardar(Denominacion denominacion) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().persist(denominacion);
    transaction.commit();
  }

  public Denominacion buscarPorId(int id) {
    return entityManager().find(Denominacion.class, id);
  }

  public void actualizar(Denominacion denominacion) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().merge(denominacion);
    transaction.commit();
  }

  public void eliminar(Denominacion denominacion) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().remove(denominacion);
    transaction.commit();
  }

  public List<Denominacion> buscarTodas() {
    TypedQuery<Denominacion> query = entityManager().createQuery("FROM " + Denominacion.class.getName(), Denominacion.class);
    return query.getResultList();
  }
}