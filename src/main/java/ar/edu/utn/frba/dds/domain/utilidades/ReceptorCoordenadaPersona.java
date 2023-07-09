package ar.edu.utn.frba.dds.domain.utilidades;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.incidentes.DetectorIncidentesCercanos;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

public class ReceptorCoordenadaPersona {
  @Getter  @Setter
  private List<Persona> personas;
  private DetectorIncidentesCercanos detectorIncidentesCercanos;

  public ReceptorCoordenadaPersona() {
    this.personas = new ArrayList<Persona>();
    this.detectorIncidentesCercanos = new DetectorIncidentesCercanos();
  }

  public ReceptorCoordenadaPersona(List<Persona> personas, DetectorIncidentesCercanos detector) {
    this.personas = personas;
    this.detectorIncidentesCercanos = detector;
  }

  public void recibirCoordenada(float latitud, float longitud, int id) {
    Optional<Persona> personaBuscada = buscarPersonaConIdUsuario(id);
    if (personaBuscada.isPresent()) {
      Persona unaPersona = personaBuscada.get();
      //1. actualizo coordenadas de persona
      unaPersona.getUbicacionActual().getCoordenada().setLatitudLongitud(latitud, longitud);
      //2. detecto incidentes cercanos con su nueva ubicación
      detectorIncidentesCercanos.evaluarSolicitudDeRevision(unaPersona);
    }
  }

  private Optional<Persona> buscarPersonaConIdUsuario(int idUsuario) {
    return this.personas.stream()
        .filter(p -> p.getUsuario().getId() == idUsuario)
        .findAny();
  }
}
