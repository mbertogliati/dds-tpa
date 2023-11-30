package ar.edu.utn.frba.dds.repositorios.incidentes;

import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.modelos.utilidades.CalculadoraDistanciaEnMetros;
import ar.edu.utn.frba.dds.modelos.utilidades.EvaluadorSolicitudRevision;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class IncidentePorComunidadRepositorio implements WithSimplePersistenceUnit {

  public void guardar(IncidentePorComunidad incidentePorComunidad) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().persist(incidentePorComunidad);
    transaction.commit();
  }

  public IncidentePorComunidad buscarPorId(int id) {
    return entityManager().find(IncidentePorComunidad.class, id);
  }

  public void actualizar(IncidentePorComunidad incidentePorComunidad) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().merge(incidentePorComunidad);
    transaction.commit();
  }

  public void eliminar(IncidentePorComunidad incidentePorComunidad) {
    EntityTransaction transaction = entityManager().getTransaction();
    transaction.begin();
    entityManager().remove(incidentePorComunidad);
    transaction.commit();
  }

  public List<IncidentePorComunidad> buscarTodos() {
    TypedQuery<IncidentePorComunidad> query = entityManager().createQuery("FROM " + IncidentePorComunidad.class.getName(), IncidentePorComunidad.class);
    return query.getResultList();
  }

  public List<IncidentePorComunidad> buscarPorIncidente(String idIncidente) {
    return (List<IncidentePorComunidad>) entityManager().createQuery(
            "SELECT i FROM IncidentePorComunidad i WHERE i.incidente.id = :idBuscado")
        .setParameter("idBuscado", Integer.parseInt(idIncidente))
        .getResultList();
  }

  public List<IncidentePorComunidad> incidentesComunidadDe(Usuario usuario, List<Incidente> incidentes){
    List<Comunidad> comunidadesPersona = usuario.getPersonaAsociada().getMembresias().stream().map(m -> m.getComunidad()).toList();

    List<IncidentePorComunidad> incidentesFiltrados = new ArrayList<>();

    for (Comunidad comunidad : comunidadesPersona){
      for (Incidente incidente : incidentes){
        if(comunidad.tieneIncidente(incidente) && incidentesFiltrados.stream().allMatch(i -> i.getIncidente().getId() != incidente.getId())){
          incidentesFiltrados.add(comunidad.obtenerIncidenteComunidad(incidente));
        }
      }
    }

    return incidentesFiltrados;
  }

  public List<IncidentePorComunidad> incidentesEnRevision(Persona persona, Comunidad comunidad){
    EvaluadorSolicitudRevision evaluadorSolicitudRevision = new EvaluadorSolicitudRevision(new CalculadoraDistanciaEnMetros());

    return evaluadorSolicitudRevision.obtenerIncidentesPorComunidadCercanos(persona.getUltimaUbicacion().getCoordenada(), comunidad);
  }

  public void refresh(IncidentePorComunidad incidentePorComunidad) {
    this.entityManager().refresh(incidentePorComunidad);
  }
}