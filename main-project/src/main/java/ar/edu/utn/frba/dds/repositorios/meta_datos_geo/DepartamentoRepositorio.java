package ar.edu.utn.frba.dds.repositorios.meta_datos_geo;

import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class DepartamentoRepositorio implements WithSimplePersistenceUnit {

  public void guardar(Departamento departamento) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().persist(departamento);
    transaction.commit();
  }

  public Departamento buscarPorId(String id) {
    return entityManager().find(Departamento.class, id);
  }

  public void actualizar(Departamento departamento) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().merge(departamento);
    transaction.commit();
  }

  public void eliminar(Departamento departamento) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().remove(departamento);
    transaction.commit();
  }

  public List<Departamento> buscarTodos() {
    TypedQuery<Departamento> query = entityManager().createQuery("SELECT d FROM Departamento d ORDER BY d.nombre", Departamento.class);
    return query.getResultList();
  }

  public List<Departamento> buscarPorProvincia(Integer idProvincia) {
    return entityManager().createQuery(
        "SELECT d FROM Departamento d WHERE d.provincia.id = :idBuscado ORDER BY d.nombre", Departamento.class)
        .setParameter("idBuscado", idProvincia)
        .getResultList();
  }

  public void refresh(Departamento departamento) {
    this.entityManager().refresh(departamento);
  }
}
