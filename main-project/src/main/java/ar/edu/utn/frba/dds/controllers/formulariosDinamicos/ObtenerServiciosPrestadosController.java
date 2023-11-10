package ar.edu.utn.frba.dds.controllers.formulariosDinamicos;

import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioPrestadoRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.List;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class ObtenerServiciosPrestadosController implements Handler {
  ServicioPrestadoRepositorio servicioPrestadoRepositorio;

  public ObtenerServiciosPrestadosController(EntityManager entityManager){
    this.servicioPrestadoRepositorio = new ServicioPrestadoRepositorio(entityManager);
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    String establecimientoId = context.queryParam("selectorId");


    if(establecimientoId != null && establecimientoId != "") {
      List<ServicioPrestado> servicioPrestados = servicioPrestadoRepositorio.buscarPorEstablecimiento(establecimientoId);

      // Genero HTML con las opciones de los departamentos
      StringBuilder htmlOptions = new StringBuilder();

      for (ServicioPrestado servicioPrestado : servicioPrestados) {
        htmlOptions
            .append("<option value=\"")
            .append(servicioPrestado.getId())
            .append(" - ")
            .append(servicioPrestado.getServicio().getNombre())
            .append("\" label=\"")
            .append(servicioPrestado.getServicio().getStringEtiquetas())
            .append("\">");
      }

      context.contentType("text/html");
      context.result(htmlOptions.toString());
    }else{
      context.contentType("text/html");
      context.result("");
    }
  }
}
