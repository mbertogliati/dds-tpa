package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.CreadorEntityManager;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidenteRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioPrestadoRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class CreadorIncidenteController implements Handler {
  IncidenteRepositorio incidenteRepositorio;
  ServicioPrestadoRepositorio servicioPrestadoRepositorio;

  public CreadorIncidenteController(EntityManager entityManager){
    this.incidenteRepositorio = new IncidenteRepositorio(entityManager);
    this.servicioPrestadoRepositorio = new ServicioPrestadoRepositorio(entityManager);
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    if(VerificadorLogueo.noEstaLogueado(context.sessionAttribute("logueado"))){
      context.redirect("/login");
      return;
    }

    Map<String, Object> model = new HashMap<>();

    String observaciones = context.formParam("observaciones");
    List<String> servicios = context.formParams("servicio[]");

    Incidente incidente = new Incidente();
    incidente.setObservaciones(observaciones);

    for (int i = 0; i < servicios.size(); i++) {
      ServicioPrestado servicioPrestado = servicioPrestadoRepositorio.buscarPorId(obtenerIdServicioPrestado(servicios.get(i)));
      incidente.agregarServiciosPrestados(servicioPrestado);
    }

    //TODO: MANEJAR EL ID DE LA PERSONA POR SESION, Y HACER COMUNIDAD.AGREGARINCIDENTE()

    incidenteRepositorio.guardar(incidente);


    context.redirect("/incidentes");
  }

  private Integer obtenerIdServicioPrestado(String texto){
    int indiceEspacio = texto.indexOf(" ");
    return Integer.parseInt(texto.substring(0, indiceEspacio));
  }
}
