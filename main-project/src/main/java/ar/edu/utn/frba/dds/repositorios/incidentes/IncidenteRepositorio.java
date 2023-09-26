package ar.edu.utn.frba.dds.repositorios.incidentes;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class IncidenteRepositorio {

  private final EntityManager entityManager;

  public IncidenteRepositorio(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void guardar(Incidente incidente) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(incidente);
    transaction.commit();
  }

  public Incidente buscarPorId(int id) {
    return entityManager.find(Incidente.class, id);
  }

  public void actualizar(Incidente incidente) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.merge(incidente);
    transaction.commit();
  }

  public void eliminar(Incidente incidente) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.remove(incidente);
    transaction.commit();
  }

  public List<Incidente> buscarTodos() {
    TypedQuery<Incidente> query = entityManager.createQuery("FROM " + Incidente.class.getName(), Incidente.class);
    return query.getResultList();
  }

  public List<Incidente> buscarPorLocalidad(String idLocalidad) {
    return (List<Incidente>) entityManager.createQuery(
            "SELECT i FROM Incidente i JOIN i.serviciosAfectados s WHERE s.establecimiento.entidad.ubicacion.metadato.localidad.id = :idBuscado")
        .setParameter("idBuscado", idLocalidad)
        .getResultList();
  }

  public List<Incidente> incidentesDeEstado(String estado, int idPersona){
    IncidentePorComunidadRepositorio repoIncidenteComunidad = new IncidentePorComunidadRepositorio(entityManager);
    PersonaRepositorio repoPersona = new PersonaRepositorio(entityManager);

    List<Incidente> incidentesTotales = this.buscarTodos();

    Estado estadoBuscado;
    switch (estado){
      case "abierto":
        estadoBuscado = Estado.ABIERTO;
        break;
      case "cerrado":
        estadoBuscado = Estado.CERRADO;
        break;
      case "paraRevision":
        estadoBuscado = Estado.REVISION;
        break;
      default:
        estadoBuscado = Estado.ABIERTO;
        break;
    }

    List<Incidente> incidentesResultado = new ArrayList<>();

    for (Incidente incidente : incidentesTotales){
      List<IncidentePorComunidad> incidentesPorComunidad = repoIncidenteComunidad.buscarPorIncidente(String.valueOf(incidente.getId()));

      switch (estadoBuscado){
        case ABIERTO -> {
          if(incidentesPorComunidad.stream().anyMatch(i -> !i.isEstaCerrado())){
            incidentesResultado.add(incidente);
          }
        }

        case CERRADO -> {
          if(incidentesPorComunidad.stream().allMatch(i -> i.isEstaCerrado())){
            incidentesResultado.add(incidente);
          }
        }

        case REVISION -> {
          //TODO: RETORNA LOS QUE SON DE INTERES PARA LA PERSONA, CONSIDERANDOLOS COMO SI FUESEN DE REVISION. DISCUTIR SI DEBERÍAMOS APLICAR LO DE INCIDENTES CERCANOS
          Persona persona = repoPersona.buscarPorId(idPersona);
          if(incidente.getServiciosAfectados().stream().anyMatch(s -> persona.getInteres().servicioPrestadoEsDeInteres(s))){
            incidentesResultado.add(incidente);
          }
        }
      }

    }

    return incidentesResultado;
  }

  enum Estado{
    ABIERTO,
    CERRADO,
    REVISION
  }
}