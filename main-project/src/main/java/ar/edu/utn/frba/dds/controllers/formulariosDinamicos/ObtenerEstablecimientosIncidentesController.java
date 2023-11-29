package ar.edu.utn.frba.dds.controllers.formulariosDinamicos;

import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.repositorios.entidades.EstablecimientoRepositorio;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import io.javalin.http.Handler;
import java.util.List;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class ObtenerEstablecimientosIncidentesController implements Handler {
  EstablecimientoRepositorio establecimientoRepositorio;

  public ObtenerEstablecimientosIncidentesController(){
    this.establecimientoRepositorio = new EstablecimientoRepositorio();
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    String entidadId = context.queryParam("selectorId");

    if(entidadId != null && entidadId != "") {
      List<Establecimiento> establecimientos = FiltradorPorComunidad.establecimientosDeComunidad(context.sessionAttribute("comunidad"),establecimientoRepositorio.buscarPorEntidad(entidadId));

      // Genero HTML con las opciones de los departamentos
      StringBuilder htmlOptions = new StringBuilder();

      for (Establecimiento establecimiento : establecimientos) {
        htmlOptions.append("<option value=\"").append(establecimiento.getId()).append("\">")
            .append(establecimiento.getNombre()).append("</option>");
      }

      context.contentType("text/html");
      context.result(htmlOptions.toString());
    }else{
      context.contentType("text/html");
      context.result("");
    }
  }
}
