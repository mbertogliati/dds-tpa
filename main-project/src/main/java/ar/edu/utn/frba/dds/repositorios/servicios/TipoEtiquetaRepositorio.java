package ar.edu.utn.frba.dds.repositorios.servicios;

import ar.edu.utn.frba.dds.modelos.servicios.Etiqueta;
import ar.edu.utn.frba.dds.modelos.servicios.TipoEtiquetas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class TipoEtiquetaRepositorio {

  private final EntityManager entityManager;

  public TipoEtiquetaRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(TipoEtiquetas tipoEtiquetas) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(tipoEtiquetas);
    transaction.commit();
  }

  public TipoEtiquetas buscarPorId(int id) {
    return entityManager.find(TipoEtiquetas.class, id);
  }

  public void actualizar(TipoEtiquetas tipoEtiquetas) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(tipoEtiquetas);
    transaction.commit();
  }

  public void eliminar(TipoEtiquetas tipoEtiquetas) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(tipoEtiquetas);
    transaction.commit();
  }

  public List<TipoEtiquetas> buscarTodos() {
    TypedQuery<TipoEtiquetas> query = entityManager.createQuery("FROM " + TipoEtiquetas.class.getName() + " t ORDER BY t.nombre ASC", TipoEtiquetas.class);
    return query.getResultList();
  }
}