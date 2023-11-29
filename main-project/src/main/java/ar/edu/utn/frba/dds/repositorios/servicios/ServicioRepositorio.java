package ar.edu.utn.frba.dds.repositorios.servicios;

import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ServicioRepositorio implements WithSimplePersistenceUnit {

  public void guardar(Servicio servicio) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().persist(servicio);
    transaction.commit();
  }

  public Servicio buscarPorId(int id) {
    return entityManager().find(Servicio.class, id);
  }

  public void actualizar(Servicio servicio) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().merge(servicio);
    transaction.commit();
  }

  public void eliminar(Servicio servicio) {
    servicio.setActivo(false);
    this.actualizar(servicio);
    //entityManager().clear();
  }

  public List<Servicio> buscarTodos() {
    return entityManager().createQuery(
            "SELECT e FROM " + Servicio.class.getName() + " e WHERE e.activo=TRUE", Servicio.class)
        .getResultList();
  }
}