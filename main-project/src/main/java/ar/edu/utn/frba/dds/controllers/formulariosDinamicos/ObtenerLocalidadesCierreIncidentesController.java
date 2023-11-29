package ar.edu.utn.frba.dds.controllers.formulariosDinamicos;

import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.LocalidadRepositorio;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import io.javalin.http.Handler;
import java.util.List;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class ObtenerLocalidadesCierreIncidentesController implements Handler {
  LocalidadRepositorio localidadRepositorio;

  public ObtenerLocalidadesCierreIncidentesController(){
    this.localidadRepositorio = new LocalidadRepositorio();
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    String departamentoId = context.queryParam("selectorId");


    if(departamentoId != null && departamentoId != "") {

      List<Localidad> localidades = FiltradorPorComunidad.localidadesConIncidentes(context.sessionAttribute("comunidad"), localidadRepositorio.buscarPorDepartamento(Integer.parseInt(departamentoId)));

      // Genero HTML con las opciones de los departamentos
      StringBuilder htmlOptions = new StringBuilder();

      for (Localidad localidad : localidades) {
        htmlOptions.append("<option value=\"").append(localidad.getId()).append("\">")
            .append(localidad.getNombre()).append("</option>");
      }

      context.contentType("text/html");
      context.result(htmlOptions.toString());
    }else{
      context.contentType("text/html");
      context.result("");
    }
  }
}
