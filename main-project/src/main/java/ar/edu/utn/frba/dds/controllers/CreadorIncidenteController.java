package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.CreadorEntityManager;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
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

    //INCIDENTE
    Incidente incidente = new Incidente();

    //OBSERVACIONES
    String observaciones = context.formParam("observaciones");
    incidente.setObservaciones(observaciones);

    //SERVICIOS
    List<String> servicios = context.formParams("servicio[]");
    for (int i = 0; i < servicios.size(); i++) {
      ServicioPrestado servicioPrestado = servicioPrestadoRepositorio.buscarPorId(obtenerIdServicioPrestado(servicios.get(i)));
      incidente.agregarServiciosPrestados(servicioPrestado);
    }

    //PERSONA
    Persona persona = context.sessionAttribute("persona");
    incidente.setAutorApertura(persona);
    incidente.agregarIncidenteComunidad();

    //PERSISTIR
    incidenteRepositorio.guardar(incidente);

    //TODO: ERROR GRAVE: CUANDO NOTIFICA, GUARDA EN BD NULL PARA EL MOMENTO DE NOTIFICACION. MIENTRAS, PUSE PARA QUE SIEMPRE SEA AL MOMENTO.
    //TODO: HAY QUE PERSISTIR NOTIFICABLECONFECHA, QUE TIENE UN NOTIFICABLE, PERO NO PERSISTIMOS NOTIFICABLES. REVISAR CÓMO HACEMOS ESO.

    context.redirect("/incidentes?success=abierto");
  }

  private Integer obtenerIdServicioPrestado(String texto){
    int indiceEspacio = texto.indexOf(" ");
    return Integer.parseInt(texto.substring(0, indiceEspacio));
  }
}
