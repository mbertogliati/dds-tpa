package ar.edu.utn.frba.dds.domain.utilidades;

import ar.edu.utn.frba.dds.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.domain.incidentes.RevisionIncidente;
import ar.edu.utn.frba.dds.notificaciones.Notificador;
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
        .map(m -> m.getComunidad())
        .toList();

    for (Comunidad unaComunidad : comunidades) {
      Coordenada coordenadaReferencia = unaPersona.getUbicacionActual().getCoordenada();
      List<IncidentePorComunidad> incidentesCercanos = this.obtenerIncidentesPorComunidadCercanos(coordenadaReferencia, unaComunidad);
      if (incidentesCercanos.size() > 0) {
        this.notificarIncidentes(unaPersona, incidentesCercanos);
      }
    }
  }

  private List<IncidentePorComunidad> obtenerIncidentesPorComunidadCercanos(Coordenada coordenada, Comunidad comunidad) {
    return comunidad.getIncidentes()
        .stream()
        .filter(i -> i.getIncidente().getServiciosAfectados().stream().anyMatch(
            s -> this.adapterCalculadoraDistancia.distanciaEntre(s.getUbicacion().getCoordenada(), coordenada) <= this.rangoCercaniaEnMetros
        )).toList();
  }

  private void notificarIncidentes(Persona persona, List<IncidentePorComunidad> incidentes) {
    for(IncidentePorComunidad incidentePorComunidad : incidentes) {
      Incidente infoIncidente = incidentePorComunidad.getIncidente();
      RevisionIncidente nuevaRevisionIncidente = new RevisionIncidente(infoIncidente);
      Notificador.notificar(nuevaRevisionIncidente, persona);
    }
  }
}
