package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.CreadorEntityManager;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.modelos.rankings.Ranking;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidentePorComunidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidenteRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioPrestadoRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public class IncidentesController implements Handler {
  IncidenteRepositorio repositorio;
  IncidentePorComunidadRepositorio repoComunidad;

  public IncidentesController(EntityManager entityManager){
    this.repositorio = new IncidenteRepositorio(entityManager);
    this.repoComunidad = new IncidentePorComunidadRepositorio(entityManager);
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    if(VerificadorLogueo.noEstaLogueado(context.sessionAttribute("logueado"))){
      context.redirect("/login");
      return;
    }

    Map<String, Object> model = new HashMap<>();

    String paramSuccess = context.queryParam("success");

    if(paramSuccess != null){
      model.put("success", new Success(paramSuccess));
    }

    List<Incidente> listaIncidentes;
    String paramEstado = context.queryParam("estado");


    if(paramEstado != null){
      Persona persona = context.sessionAttribute("persona");
      listaIncidentes = repositorio.incidentesDeEstado(paramEstado, persona.getId());
    }else{
      listaIncidentes = repositorio.buscarTodos();
    }

    List<IncidentePorComunidad> incidentesPorComunidad = repoComunidad.buscarTodos();

    listaIncidentes.forEach(i -> i.actualizarEstado(incidentesPorComunidad));

    model.put("incidentes", listaIncidentes);

    context.render("listaIncidentes.hbs", model);
  }

  @Getter
  @Setter
  private class Success{
    private String caso;

    public Success(String caso){
      this.caso = caso;
    }
  }

}
