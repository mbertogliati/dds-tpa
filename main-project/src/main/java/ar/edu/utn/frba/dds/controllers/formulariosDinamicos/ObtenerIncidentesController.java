package ar.edu.utn.frba.dds.controllers.formulariosDinamicos;

import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidenteRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioPrestadoRepositorio;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import io.javalin.http.Handler;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class ObtenerIncidentesController implements Handler {
  IncidenteRepositorio incidenteRepositorio;

  public ObtenerIncidentesController(){
    this.incidenteRepositorio = new IncidenteRepositorio();
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    String localidadId = context.queryParam("selectorId");


    if(localidadId != null && localidadId != "") {
      List<Incidente> incidentes = incidenteRepositorio.buscarPorLocalidad(localidadId);
      List<Incidente> incidentesAux = new ArrayList<>();

      for (Incidente incidente : incidentes){
        if(incidentesAux.stream().allMatch(i -> i.getId() != incidente.getId())){
          incidentesAux.add(incidente);
        }
      }

      // Genero HTML con las opciones de los departamentos
      StringBuilder htmlOptions = new StringBuilder();

      for (Incidente incidente : incidentesAux) {
        StringBuilder servicioText = new StringBuilder();
        servicioText.append(incidente.getServiciosAfectados().get(0).getEstablecimiento().getNombre());

        for (ServicioPrestado servicioPrestado : incidente.getServiciosAfectados()){
          servicioText.append(" - ").append(servicioPrestado.getServicio().getNombre());
        }

        htmlOptions
            .append("<option value=\"")
            .append(incidente.getId())
            .append("\">")
            .append(servicioText.toString())
            .append("</option>");
      }

      context.contentType("text/html");
      context.result(htmlOptions.toString());
    }else{
      context.contentType("text/html");
      context.result("");
    }
  }

  private String obtenerTextoServicioPrestado(ServicioPrestado servicioPrestado){
    return " - " + servicioPrestado.getServicio().getNombre();
  }
}
