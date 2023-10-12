package ar.edu.utn.frba.dds.repositorios.servicios;

import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ServicioPrestadoRepositorio {

  private final EntityManager entityManager;

  public ServicioPrestadoRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(ServicioPrestado servicioPrestado) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(servicioPrestado);
    transaction.commit();
  }

  public ServicioPrestado buscarPorId(int id) {
    return entityManager.find(ServicioPrestado.class, id);
  }

  public void actualizar(ServicioPrestado servicioPrestado) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(servicioPrestado);
    transaction.commit();
  }

  public void eliminar(ServicioPrestado servicioPrestado) {
    servicioPrestado.setActivo(false);
    this.actualizar(servicioPrestado);
    //entityManager.clear();
  }

  public List<ServicioPrestado> buscarTodos() {
    return entityManager.createQuery(
            "SELECT e FROM " + ServicioPrestado.class.getName() + " e WHERE e.activo=1", ServicioPrestado.class)
        .getResultList();
  }

  public List<ServicioPrestado> buscarPorEstablecimiento(String idEstablecimiento) {
    return entityManager.createQuery(
            "SELECT s FROM " + ServicioPrestado.class.getName() + " s WHERE s.establecimiento.id = :idBuscado AND s.activo=1", ServicioPrestado.class)
        .setParameter("idBuscado", Integer.parseInt(idEstablecimiento))
        .getResultList();
  }

}