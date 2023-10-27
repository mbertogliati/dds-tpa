package ar.edu.utn.frba.dds.controllers.formulariosDinamicos;

import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EstablecimientoRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.List;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class ObtenerEstablecimientosController implements Handler {
  EstablecimientoRepositorio establecimientoRepositorio;

  public ObtenerEstablecimientosController(EntityManager entityManager){
    this.establecimientoRepositorio = new EstablecimientoRepositorio(entityManager);
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    String entidadId = context.queryParam("selectorId");

    List<Establecimiento> establecimientos = establecimientoRepositorio.buscarPorEntidad(entidadId);

    // Genero HTML con las opciones de los departamentos
    StringBuilder htmlOptions = new StringBuilder();

    for (Establecimiento establecimiento : establecimientos) {
      htmlOptions.append("<option value=\"").append(establecimiento.getId()).append("\">")
          .append(establecimiento.getNombre()).append("</option>");
    }

    context.contentType("text/html");
    context.result(htmlOptions.toString());
  }
}
