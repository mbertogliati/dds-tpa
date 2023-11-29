package ar.edu.utn.frba.dds.modelos.utilidades;

import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Membresia;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.modelos.incidentes.RevisionIncidente;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificador;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class EvaluadorSolicitudRevision {
  @Setter
  private AdapterCalculadoraDistancia adapterCalculadoraDistancia;

  @Getter @Setter
  private int rangoCercaniaEnMetros = 500;

  public EvaluadorSolicitudRevision() {
    this.adapterCalculadoraDistancia = null;
    this.inicializarRangoCercania();
  }

  public EvaluadorSolicitudRevision(AdapterCalculadoraDistancia calculadoraDistancia) {
    this.adapterCalculadoraDistancia = calculadoraDistancia;
    this.inicializarRangoCercania();
  }

  private void inicializarRangoCercania() {
    String envRangoCercaniaEnMetros = System.getenv("EVALUADOR_SOL_REVISION_RANGO_CERCANIA_METROS");
    if( envRangoCercaniaEnMetros == null ||
        envRangoCercaniaEnMetros.isEmpty() ||
        envRangoCercaniaEnMetros.isBlank())
      return;
    this.rangoCercaniaEnMetros = Integer.parseInt(envRangoCercaniaEnMetros);
  }

  public void evaluarSolicitudRevision(Persona unaPersona) {
    List<Comunidad> comunidades = unaPersona.getMembresias().stream()
        .map(Membresia::getComunidad)
        .toList();

    for (Comunidad unaComunidad : comunidades) {
      Coordenada coordenadaReferencia = unaPersona.getUltimaUbicacion().getCoordenada();
      List<IncidentePorComunidad> incidentesCercanos = this.obtenerIncidentesPorComunidadCercanos(coordenadaReferencia, unaComunidad);
      if (!incidentesCercanos.isEmpty()) {
        this.notificarIncidentes(unaPersona, incidentesCercanos);
      }
    }
  }

  public List<IncidentePorComunidad> obtenerIncidentesPorComunidadCercanos(Coordenada coordenada, Comunidad comunidad) {
    List<IncidentePorComunidad> incidentesPorComunidad = comunidad.getIncidentes();
    if (!incidentesPorComunidad.isEmpty()){
      return incidentesPorComunidad.stream()
          .filter(i -> !i.isEstaCerrado() && i.getIncidente().getServiciosAfectados().stream().anyMatch(
              s -> s.getUbicacion().getCoordenada() != null && this.adapterCalculadoraDistancia.distanciaEntre(s.getUbicacion().getCoordenada(), coordenada) <= this.rangoCercaniaEnMetros
          )).toList();
    }else {
      return incidentesPorComunidad;
    }

  }

  public List<IncidentePorComunidad> obtenerIncidentesCercanos(Persona unaPersona) {
    List<Comunidad> comunidades = unaPersona.getMembresias().stream()
        .map(Membresia::getComunidad)
        .toList();

    List<IncidentePorComunidad> incidentesPorComunidad = new ArrayList<>();

    for (Comunidad unaComunidad : comunidades) {
      Coordenada coordenadaReferencia = unaPersona.getUltimaUbicacion().getCoordenada();
      List<IncidentePorComunidad> incidentesCercanos = this.obtenerIncidentesPorComunidadCercanos(coordenadaReferencia, unaComunidad);
      incidentesPorComunidad.addAll(incidentesCercanos);
    }

    return incidentesPorComunidad;
  }

  private void notificarIncidentes(Persona persona, List<IncidentePorComunidad> incidentes) {
    for(IncidentePorComunidad incidentePorComunidad : incidentes) {
      Incidente infoIncidente = incidentePorComunidad.getIncidente();
      RevisionIncidente nuevaRevisionIncidente = new RevisionIncidente(infoIncidente);
      Notificador.notificar(nuevaRevisionIncidente, persona);
    }
  }
}
