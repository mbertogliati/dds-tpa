package ar.edu.utn.frba.dds.controllers.formulariosDinamicos;

import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.DepartamentoRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.List;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class ObtenerDepartamentosController implements Handler {
  DepartamentoRepositorio departamentoRepositorio;

  public ObtenerDepartamentosController(EntityManager entityManager){
    this.departamentoRepositorio = new DepartamentoRepositorio(entityManager);
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    String provinciaId = context.queryParam("selectorId");

    List<Departamento> departamentos = departamentoRepositorio.buscarPorProvincia(provinciaId);

    // Genero HTML con las opciones de los departamentos
    StringBuilder htmlOptions = new StringBuilder();

    for (Departamento departamento : departamentos) {
      htmlOptions.append("<option value=\"").append(departamento.getId()).append("\">")
          .append(departamento.getNombre()).append("</option>");
    }

    context.contentType("text/html");
    context.result(htmlOptions.toString());
  }
}
