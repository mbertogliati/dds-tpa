package ar.edu.utn.frba.dds.repositorios.comunidades;

import ar.edu.utn.frba.dds.controllers.utils.TipoRol;
import ar.edu.utn.frba.dds.modelos.comunidades.Rol;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class RolRepositorio implements WithSimplePersistenceUnit {

  public void guardar(Rol rol) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().persist(rol);
    transaction.commit();
  }

  public Rol buscarPorId(int id) {
    return entityManager().find(Rol.class, id);
  }

  public void actualizar(Rol rol) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().merge(rol);
    transaction.commit();
  }

  public void eliminar(Rol rol) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().remove(rol);
    transaction.commit();
  }

  public List<Rol> buscarTodos() {
    TypedQuery<Rol> query = entityManager().createQuery("FROM " + Rol.class.getName(), Rol.class);
    return query.getResultList();
  }

  public Rol rolAdminPlataforma(){return this.buscarPorId(TipoRol.ADMINISTRADOR.ordinal());}
  public Rol rolDefault(){
    return this.buscarPorId(TipoRol.DEFAULT.ordinal());
  }
  public Rol rolAdminComunidad(){return this.buscarPorId(TipoRol.ADMINISTRADOR_COMUNIDAD.ordinal());}
  public Rol rolDefaultComunidad(){return this.buscarPorId(TipoRol.DEFAULT_COMUNIDAD.ordinal());}
}
