package ar.edu.utn.frba.dds.controllers.formulariosDinamicos;

import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.List;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class ObtenerEntidadesIncidentesController implements Handler {
  EntidadRepositorio entidadRepositorio;

  public ObtenerEntidadesIncidentesController(EntityManager entityManager){
    this.entidadRepositorio = new EntidadRepositorio(entityManager);
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    String localidadId = context.queryParam("selectorId");

    if(localidadId != null && localidadId != "") {
      List<Entidad> entidades = FiltradorPorComunidad.entidadesDeComunidad(context.sessionAttribute("comunidad"),entidadRepositorio.buscarPorLocalidad(localidadId));

      // Genero HTML con las opciones de los departamentos
      StringBuilder htmlOptions = new StringBuilder();

      for (Entidad entidad : entidades) {
        htmlOptions.append("<option value=\"").append(entidad.getId()).append("\">")
            .append(entidad.getNombre()).append("</option>");
      }

      context.contentType("text/html");
      context.result(htmlOptions.toString());
    }else{
      context.contentType("text/html");
      context.result("");
    }
  }
}
