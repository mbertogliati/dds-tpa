package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidenteRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class IncidentesController implements Handler {
  IncidenteRepositorio repositorio;

  public IncidentesController(IncidenteRepositorio repositorio){
    this.repositorio = repositorio;
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    Map<String, Object> model = new HashMap<>();

    List<Incidente> incidentesRecuperados = repositorio.buscarTodos();

    model.put("incidentes", incidentesRecuperados);

    context.render("listaIncidentes.hbs", model);
  }
}
