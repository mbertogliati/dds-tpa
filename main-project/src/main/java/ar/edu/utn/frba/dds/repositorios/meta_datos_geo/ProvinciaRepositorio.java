package ar.edu.utn.frba.dds.repositorios.meta_datos_geo;

import ar.edu.utn.frba.dds.domain.meta_datos_geo.Provincia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class ProvinciaRepositorio {

  private final EntityManager entityManager;

  public ProvinciaRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Provincia provincia) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(provincia);
    transaction.commit();
  }

  public Provincia buscarPorId(String id) {
    return entityManager.find(Provincia.class, id);
  }

  public void actualizar(Provincia provincia) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(provincia);
    transaction.commit();
  }

  public void eliminar(Provincia provincia) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(provincia);
    transaction.commit();
  }

  public List<Provincia> buscarTodas() {
    TypedQuery<Provincia> query = entityManager.createQuery("FROM " + Provincia.class.getName(), Provincia.class);
    return query.getResultList();
  }
}