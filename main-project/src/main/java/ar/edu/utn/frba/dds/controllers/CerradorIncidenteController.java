package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidenteRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class CerradorIncidenteController implements Handler {
  IncidenteRepositorio repoIncidente;
  PersonaRepositorio repoPersona;

  public CerradorIncidenteController(EntityManager entityManager){
    this.repoIncidente = new IncidenteRepositorio(entityManager);
    this.repoPersona = new PersonaRepositorio(entityManager);
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    if(VerificadorLogueo.noEstaLogueado(context.sessionAttribute("logueado"))){
      context.redirect("/login");
      return;
    }

    //INCIDENTE
    String idIncidente = context.formParam("incidente");
    Incidente incidente = this.repoIncidente.buscarPorId(Integer.parseInt(idIncidente));

    //PERSONA
    Persona persona = context.sessionAttribute("persona");

    //CERRAR INCIDENTE
    persona.cerrarIncidente(incidente);

    //PERSISTIR
    this.repoIncidente.actualizar(incidente);

    //TODO: ERROR GRAVE: CUANDO NOTIFICA, GUARDA EN BD NULL PARA EL MOMENTO DE NOTIFICACION. MIENTRAS, PUSE PARA QUE SIEMPRE SEA AL MOMENTO.
    //TODO: HAY QUE PERSISTIR NOTIFICABLECONFECHA, QUE TIENE UN NOTIFICABLE, PERO NO PERSISTIMOS NOTIFICABLES. REVISAR CÓMO HACEMOS ESO.

    context.redirect("/incidentes?success=cerrado");
  }
}
