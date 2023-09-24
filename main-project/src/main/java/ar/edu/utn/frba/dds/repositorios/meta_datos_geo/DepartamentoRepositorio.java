package ar.edu.utn.frba.dds.repositorios.meta_datos_geo;

import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class DepartamentoRepositorio {

  private final EntityManager entityManager;

  public DepartamentoRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Departamento departamento) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(departamento);
    transaction.commit();
  }

  public Departamento buscarPorId(String id) {
    return entityManager.find(Departamento.class, id);
  }

  public void actualizar(Departamento departamento) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(departamento);
    transaction.commit();
  }

  public void eliminar(Departamento departamento) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(departamento);
    transaction.commit();
  }

  public List<Departamento> buscarTodos() {
    TypedQuery<Departamento> query = entityManager.createQuery("FROM " + Departamento.class.getName(), Departamento.class);
    return query.getResultList();
  }
}
