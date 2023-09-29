package ar.edu.utn.frba.dds.repositorios.incidentes;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.modelos.utilidades.CalculadoraDistanciaEnMetros;
import ar.edu.utn.frba.dds.modelos.utilidades.EvaluadorSolicitudRevision;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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


    if(estadoBuscado == Estado.REVISION){
      Persona persona = repoPersona.buscarPorId(idPersona);
      EvaluadorSolicitudRevision evaluadorSolicitudRevision = new EvaluadorSolicitudRevision(new CalculadoraDistanciaEnMetros());
      List<IncidentePorComunidad> incidentesPorComunidadTotales = evaluadorSolicitudRevision.obtenerIncidentesCercanos(persona);

      List<Incidente> incidentesRepetidos = incidentesPorComunidadTotales.stream().map(ipc -> ipc.getIncidente()).toList();
      Set<Incidente> setSinRepetidos = new HashSet<>(incidentesRepetidos);

      List<Incidente> incidentesFinal = new ArrayList<>();
      incidentesFinal.addAll(setSinRepetidos);
      return incidentesFinal;
    }

    List<Incidente> incidentesResultado = new ArrayList<>();

    switch (estadoBuscado){

      case ABIERTO -> {
        for (Incidente incidente : incidentesTotales) {
          List<IncidentePorComunidad> incidentesPorComunidad = repoIncidenteComunidad.buscarPorIncidente(String.valueOf(incidente.getId()));
          if (incidentesPorComunidad.stream().anyMatch(i -> !i.isEstaCerrado())) {
            incidentesResultado.add(incidente);
          }
        }
      }

      case CERRADO -> {
        for (Incidente incidente : incidentesTotales) {
          List<IncidentePorComunidad> incidentesPorComunidad = repoIncidenteComunidad.buscarPorIncidente(String.valueOf(incidente.getId()));
          if (incidentesPorComunidad.stream().allMatch(i -> i.isEstaCerrado())) {
            incidentesResultado.add(incidente);
          }
        }
      }

      case REVISION -> {
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