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
  }

  public EvaluadorSolicitudRevision(AdapterCalculadoraDistancia calculadoraDistancia) {
    this.adapterCalculadoraDistancia = calculadoraDistancia;
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
    return comunidad.getIncidentes()
        .stream()
        .filter(i -> !i.isEstaCerrado() && i.getIncidente().getServiciosAfectados().stream().anyMatch(
            s -> s.getUbicacion().getCoordenada() != null && this.adapterCalculadoraDistancia.distanciaEntre(s.getUbicacion().getCoordenada(), coordenada) <= this.rangoCercaniaEnMetros
        )).toList();
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
