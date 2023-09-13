package ar.edu.utn.frba.dds.repositorios.servicios;

import ar.edu.utn.frba.dds.domain.servicios.Etiqueta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class EtiquetaRepositorio {

  private final EntityManager entityManager;

  public EtiquetaRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Etiqueta etiqueta) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(etiqueta);
    transaction.commit();
  }

  public Etiqueta buscarPorId(int id) {
    return entityManager.find(Etiqueta.class, id);
  }

  public void actualizar(Etiqueta etiqueta) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(etiqueta);
    transaction.commit();
  }

  public void eliminar(Etiqueta etiqueta) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(etiqueta);
    transaction.commit();
  }

  public List<Etiqueta> buscarTodas() {
    TypedQuery<Etiqueta> query = entityManager.createQuery("FROM " + Etiqueta.class.getName(), Etiqueta.class);
    return query.getResultList();
  }
}