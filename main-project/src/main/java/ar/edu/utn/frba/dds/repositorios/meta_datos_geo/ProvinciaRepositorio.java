package ar.edu.utn.frba.dds.repositorios.meta_datos_geo;

import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class ProvinciaRepositorio implements WithSimplePersistenceUnit {

  public void guardar(Provincia provincia) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    try{
      entityManager().persist(provincia);
    }catch(EntityExistsException e){
      System.out.println(e);
    }
    transaction.commit();
  }

  public Provincia buscarPorId(String id) {
    return entityManager().find(Provincia.class, id);
  }

  public void actualizar(Provincia provincia) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().merge(provincia);
    transaction.commit();
  }

  public void eliminar(Provincia provincia) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().remove(provincia);
    transaction.commit();
  }

  public List<Provincia> buscarTodas() {
    TypedQuery<Provincia> query = entityManager().createQuery("SELECT p FROM Provincia p ORDER BY p.nombre", Provincia.class);
    return query.getResultList();
  }

  public void refresh(Provincia provincia) {
    this.entityManager().refresh(provincia);
  }
}