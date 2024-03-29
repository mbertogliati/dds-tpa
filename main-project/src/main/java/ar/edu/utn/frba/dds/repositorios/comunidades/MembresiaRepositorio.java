package ar.edu.utn.frba.dds.repositorios.comunidades;

import ar.edu.utn.frba.dds.modelos.comunidades.Membresia;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;

import ar.edu.utn.frba.dds.modelos.fusion_organizacion.UltimoIntentoFusionComunidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class MembresiaRepositorio implements WithSimplePersistenceUnit {

  public void guardar(Membresia membresia) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().persist(membresia);
    transaction.commit();
  }

  public Membresia buscarPorId(int id) {
    return entityManager().find(Membresia.class, id);
  }

  public void actualizar(Membresia membresia) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().merge(membresia);
    transaction.commit();
  }

  public void eliminar(Membresia membresia) {
    membresia.setActivo(false);
    membresia.setRoles(new ArrayList<>());
    this.actualizar(membresia);
    //entityManager().clear();
  }

  public List<Membresia> buscarTodas() {
    return entityManager().createQuery(
            "SELECT e FROM " + Membresia.class.getName() + " e WHERE e.activo=TRUE", Membresia.class)
        .getResultList();
  }

  public void refresh(Membresia membresia) {
    entityManager().refresh(membresia);
  }
}
