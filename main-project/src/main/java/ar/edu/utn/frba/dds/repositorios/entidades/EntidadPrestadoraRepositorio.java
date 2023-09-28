package ar.edu.utn.frba.dds.repositorios.entidades;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class EntidadPrestadoraRepositorio {

  private final EntityManager entityManager;

  public EntidadPrestadoraRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(EntidadPrestadora entidadPrestadora) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(entidadPrestadora);
    transaction.commit();
  }

  public EntidadPrestadora buscarPorId(int id) {
    return entityManager.find(EntidadPrestadora.class, id);
  }

  public void actualizar(EntidadPrestadora entidadPrestadora) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(entidadPrestadora);
    transaction.commit();
  }

  public void eliminar(EntidadPrestadora entidadPrestadora) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(entidadPrestadora);
    transaction.commit();
  }

  public List<EntidadPrestadora> buscarTodos() {
    TypedQuery<EntidadPrestadora> query = entityManager.createQuery("FROM " + EntidadPrestadora.class.getName(), EntidadPrestadora.class);
    return query.getResultList();
  }

  public List<EntidadPrestadora> manejadasPor(Persona persona) {
    return (List<EntidadPrestadora>) entityManager.createQuery(
            "SELECT e FROM EntidadPrestadora e WHERE e.personaAInformar.id = :idBuscado")
        .setParameter("idBuscado", persona.getId())
        .getResultList();
  }
}