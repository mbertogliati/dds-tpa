package ar.edu.utn.frba.dds.repositorios.comunidades;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

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
    persona.setActivo(false);
    this.actualizar(persona);
    //entityManager.clear();
  }
  @Transactional
  public List<Persona> buscarTodas() {
    return entityManager.createQuery(
            "SELECT e FROM " + Persona.class.getName() + " e WHERE e.activo=TRUE", Persona.class)
        .getResultList();
  }
}