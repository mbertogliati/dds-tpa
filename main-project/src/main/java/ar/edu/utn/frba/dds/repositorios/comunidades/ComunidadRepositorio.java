package ar.edu.utn.frba.dds.repositorios.comunidades;
import ar.edu.utn.frba.dds.WithSimplePersistUnitConfigured;
import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ComunidadRepositorio implements WithSimplePersistenceUnit {

  public void guardar(Comunidad comunidad) {
    EntityTransaction transaction = entityManager().getTransaction();

    try {
      transaction.begin();
      entityManager().persist(comunidad);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  public Comunidad obtenerPorId(int id) {
    return entityManager().find(Comunidad.class, id);
  }

  public void actualizar(Comunidad comunidad) {
    EntityTransaction transaction = entityManager().getTransaction();

    try {
      transaction.begin();
      entityManager().merge(comunidad);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
    entityManager().refresh(comunidad);
  }

  public void eliminar(Comunidad comunidad) {
    comunidad.setActivo(false);
    this.actualizar(comunidad);
    //entityManager().clear();
  }
  public List<Comunidad> obtenerTodas() {
    return entityManager().createQuery(
            "SELECT e FROM " + Comunidad.class.getName() + " e WHERE e.activo=TRUE", Comunidad.class)
        .getResultList();
  }

  public void refresh(Comunidad comunidad) {
    entityManager().refresh(comunidad);
  }
}

