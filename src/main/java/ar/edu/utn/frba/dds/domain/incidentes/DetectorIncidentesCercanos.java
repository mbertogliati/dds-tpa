package ar.edu.utn.frba.dds.domain.incidentes;

import ar.edu.utn.frba.dds.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.utilidades.Coordenada;
import ar.edu.utn.frba.dds.domain.utilidades.DistanciaEntrePuntos;
import ar.edu.utn.frba.dds.notificaciones.Notificador;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class DetectorIncidentesCercanos {
  @Setter
  private DistanciaEntrePuntos adapterCalculadoraDistancia;

  @Getter @Setter
  private int RadioCercania = 500;

  public DetectorIncidentesCercanos() {
    this.adapterCalculadoraDistancia = null;
  }

  public DetectorIncidentesCercanos(DistanciaEntrePuntos calculadoraDistancia) {
    this.adapterCalculadoraDistancia = calculadoraDistancia;
  }

  public void evaluarSolicitudDeRevision(Persona unaPersona) {
    List<Comunidad> comunidades = unaPersona.getMembresias().stream()
        .map(m -> m.getComunidad())
        .toList();

    for (Comunidad unaComunidad : comunidades) {
      Coordenada coordenadaReferencia = unaPersona.getUbicacionActual().getCoordenada();
      List<IncidentePorComunidad> incidentesCercanos = this.obtenerIncidentesPorComunidadCercanos(coordenadaReferencia, unaComunidad);
      if (incidentesCercanos.size() > 0) {
        this.notificarIncidentesCercanos(unaPersona, incidentesCercanos);
      }
    }
  }

  private List<IncidentePorComunidad> obtenerIncidentesPorComunidadCercanos(Coordenada unaCoordenada, Comunidad comunidad) {
    return comunidad.getIncidentes()
        .stream()
        .filter(i -> i.getIncidente().getServiciosAfectados().stream().anyMatch(
            s -> this.adapterCalculadoraDistancia.distanciaEntre(s.getUbicacion().getCoordenada(), unaCoordenada) < this.RadioCercania
        )).toList();
  }

  private void notificarIncidentesCercanos(Persona unaPersona, List<IncidentePorComunidad> incidentesCercanos) {
    for(IncidentePorComunidad incidentePorComunidad : incidentesCercanos) {
      Incidente infoIncidente = incidentePorComunidad.getIncidente();
      RevisionIncidente nuevaRevisionIncidente = new RevisionIncidente(infoIncidente);
      Notificador.notificar(nuevaRevisionIncidente, unaPersona);
    }
  }
}
