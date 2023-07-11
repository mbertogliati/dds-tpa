package ar.edu.utn.frba.dds.controladores.utilidades;

import ar.edu.utn.frba.dds.acceso_datos.repositorios.RepositorioPersona;
import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.utilidades.EvaluadorSolicitudRevision;
import lombok.Getter;
import lombok.Setter;

public class ReceptorCoordenadaPersona {
  @Getter  @Setter
  private RepositorioPersona repositorioPersona;
  private EvaluadorSolicitudRevision evaluadorSolicitudRevision;

  public ReceptorCoordenadaPersona() {
    this.repositorioPersona = null;
    this.evaluadorSolicitudRevision = new EvaluadorSolicitudRevision();
  }

  public ReceptorCoordenadaPersona(RepositorioPersona repositorioPersona, EvaluadorSolicitudRevision detector) {
    this.repositorioPersona = repositorioPersona;
    this.evaluadorSolicitudRevision = detector;
  }

  public void recibirCoordenada(float latitud, float longitud, int id) {

    Persona persona = this.repositorioPersona.obtenerPersonaPorIdUsuario(id);

    if (persona != null) {
      //1. actualizo coordenadas de persona
      persona.getUbicacionActual().getCoordenada().setLatitudLongitud(latitud, longitud);
      repositorioPersona.modificarPersona(id, persona);

      //2. detecto incidentes cercanos con su nueva ubicación
      evaluadorSolicitudRevision.evaluarSolicitudRevision(persona);
    }
  }
}
