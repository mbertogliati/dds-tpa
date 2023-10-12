package ar.edu.utn.frba.dds.repositorios.comunidades;

import ar.edu.utn.frba.dds.modelos.comunidades.Membresia;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class MembresiaRepositorio {

  private final EntityManager entityManager;

  public MembresiaRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Membresia membresia) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(membresia);
    transaction.commit();
  }

  public Membresia buscarPorId(int id) {
    return entityManager.find(Membresia.class, id);
  }

  public void actualizar(Membresia membresia) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(membresia);
    transaction.commit();
  }

  public void eliminar(Membresia membresia) {
    membresia.setActivo(false);
    this.actualizar(membresia);
    //entityManager.clear();
  }

  public List<Membresia> buscarTodas() {
    return entityManager.createQuery(
            "SELECT e FROM " + Membresia.class.getName() + " e WHERE e.activo=1", Membresia.class)
        .getResultList();
  }
}
