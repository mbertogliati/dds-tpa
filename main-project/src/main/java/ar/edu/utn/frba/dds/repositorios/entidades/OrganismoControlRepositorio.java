package ar.edu.utn.frba.dds.repositorios.entidades;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class OrganismoControlRepositorio {

  private final EntityManager entityManager;

  public OrganismoControlRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(OrganismoControl organismoControl) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(organismoControl);
    transaction.commit();
  }

  public OrganismoControl buscarPorId(int id) {
    return entityManager.find(OrganismoControl.class, id);
  }

  public void actualizar(OrganismoControl organismoControl) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(organismoControl);
    transaction.commit();
  }

  public void eliminar(OrganismoControl organismoControl) {
    organismoControl.setActivo(false);
    this.actualizar(organismoControl);
    //entityManager.clear();
  }

  public List<OrganismoControl> buscarTodos() {
    return entityManager.createQuery(
            "FROM " + OrganismoControl.class.getName(), OrganismoControl.class)
        .getResultList();
  }


  public List<OrganismoControl> manejadosPor(Persona persona) {
    return entityManager.createQuery(
            "SELECT o FROM OrganismoControl o WHERE o.personaAInformar.id = :idBuscado AND o.activo=true", OrganismoControl.class)
        .setParameter("idBuscado", persona.getId())
        .getResultList();
  }
}