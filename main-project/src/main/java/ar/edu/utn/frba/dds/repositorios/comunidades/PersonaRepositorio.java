package ar.edu.utn.frba.dds.repositorios.comunidades;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class PersonaRepositorio {

  private final EntityManager entityManager;

  public PersonaRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Persona persona) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(persona);
    transaction.commit();
  }

  public Persona buscarPorId(int id) {
    return entityManager.find(Persona.class, id);
  }

  public void actualizar(Persona persona) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(persona);
    transaction.commit();
  }

  public void eliminar(Persona persona) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(persona);
    transaction.commit();
  }

  public List<Persona> buscarTodas() {
    TypedQuery<Persona> query = entityManager.createQuery("FROM " + Persona.class.getName(), Persona.class);
    return query.getResultList();
  }
}