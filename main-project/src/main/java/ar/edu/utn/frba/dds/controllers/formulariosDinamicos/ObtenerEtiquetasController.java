package ar.edu.utn.frba.dds.controllers.formulariosDinamicos;

import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.servicios.Etiqueta;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.modelos.servicios.TipoEtiquetas;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidenteRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.EtiquetaRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.TipoEtiquetaRepositorio;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import io.javalin.http.Handler;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class ObtenerEtiquetasController implements Handler {
  EtiquetaRepositorio etiquetaRepositorio;
  TipoEtiquetaRepositorio tipoEtiquetaRepositorio;

  public ObtenerEtiquetasController(){
    this.etiquetaRepositorio = new EtiquetaRepositorio();
    this.tipoEtiquetaRepositorio = new TipoEtiquetaRepositorio();
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    String tipoId = context.queryParam("tipoId");

    if(tipoId != null && tipoId != "") {
      List<Etiqueta> etiquetas = this.etiquetaRepositorio.buscarTodasDeTipo(Integer.parseInt(tipoId));

      // Genero HTML con las opciones de los departamentos
      StringBuilder htmlOptions = new StringBuilder();

      for (Etiqueta etiqueta : etiquetas) {
        htmlOptions
            .append("<option value=\"")
            .append(etiqueta.getId())
            .append("\">")
            .append(etiqueta.getValor())
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
