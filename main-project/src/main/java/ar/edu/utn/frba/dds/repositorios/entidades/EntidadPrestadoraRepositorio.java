package ar.edu.utn.frba.dds.repositorios.entidades;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.entidades.Denominacion;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EntidadPrestadoraRepositorio implements WithSimplePersistenceUnit {

  public void guardar(EntidadPrestadora entidadPrestadora) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().persist(entidadPrestadora);
    transaction.commit();
  }

  public EntidadPrestadora buscarPorId(int id) {
    return entityManager().find(EntidadPrestadora.class, id);
  }

  public void actualizar(EntidadPrestadora entidadPrestadora) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().merge(entidadPrestadora);
    transaction.commit();
  }

  public void eliminar(EntidadPrestadora entidadPrestadora) {
    entidadPrestadora.setActivo(false);
    this.actualizar(entidadPrestadora);
    //entityManager().clear();
  }

  public List<EntidadPrestadora> buscarTodas() {

    return entityManager().createQuery(
            "SELECT e FROM " + EntidadPrestadora.class.getName() + " e WHERE e.activo=TRUE", EntidadPrestadora.class)
        .getResultList();
  }

  public List<EntidadPrestadora> manejadasPor(Persona persona) {
    return entityManager().createQuery(
            "SELECT e FROM " + EntidadPrestadora.class.getName() +" e WHERE e.personaAInformar.id = :idBuscado AND e.activo=TRUE", EntidadPrestadora.class)
        .setParameter("idBuscado", persona.getId())
        .getResultList();
  }

  public void refresh(EntidadPrestadora entidadPrestadora) {
    this.entityManager().refresh(entidadPrestadora);
  }
}