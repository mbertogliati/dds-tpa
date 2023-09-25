package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidenteRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    String idIncidente = context.formParam("incidente");

    Incidente incidente = this.repoIncidente.buscarPorId(Integer.parseInt(idIncidente));

    //TODO: MANEJAR EL ID DE LA PERSONA POR SESION, Y HACER COMUNIDAD.CERRARINCIDENTE()
    Persona persona = repoPersona.buscarPorId(1);
    persona.getMembresias().stream().map(m -> m.getComunidad()).forEach(c -> c.cerrarIncidente(incidente, persona));

    this.repoIncidente.actualizar(incidente);

    context.redirect("/incidentes");
  }
}
