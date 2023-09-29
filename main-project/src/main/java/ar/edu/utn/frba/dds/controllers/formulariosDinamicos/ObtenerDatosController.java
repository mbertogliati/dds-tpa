package ar.edu.utn.frba.dds.controllers.formulariosDinamicos;

import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EstablecimientoRepositorio;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidenteRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.DepartamentoRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.LocalidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioPrestadoRepositorio;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

public class ObtenerDatosController {
  private EntityManager entityManager;

  public ObtenerDatosController(EntityManager entityManager){
    this.entityManager = entityManager;
  }

  public void Departamentos(Context context){
    DepartamentoRepositorio repoDepartamento = new DepartamentoRepositorio(entityManager);

    String provinciaId = context.queryParam("selectorId");

    List<Departamento> departamentos = repoDepartamento.buscarPorProvincia(provinciaId);

    // Genero HTML con las opciones de los departamentos
    StringBuilder htmlOptions = new StringBuilder();

    for (Departamento departamento : departamentos) {
      htmlOptions.append("<option value=\"").append(departamento.getId()).append("\">")
          .append(departamento.getNombre()).append("</option>");
    }

    context.contentType("text/html");
    context.result(htmlOptions.toString());
  }

  public void Localidades(Context context){
    LocalidadRepositorio repoLocalidad = new LocalidadRepositorio(entityManager);

    String departamentoId = context.queryParam("selectorId");

    List<Localidad> localidades = repoLocalidad.buscarPorDepartamento(departamentoId);

    // Genero HTML con las opciones de los departamentos
    StringBuilder htmlOptions = new StringBuilder();

    for (Localidad localidad : localidades) {
      htmlOptions.append("<option value=\"").append(localidad.getId()).append("\">")
          .append(localidad.getNombre()).append("</option>");
    }

    context.contentType("text/html");
    context.result(htmlOptions.toString());
  }

  public void Entidades(Context context){
    EntidadRepositorio repoEntidad = new EntidadRepositorio(entityManager);

    String localidadId = context.queryParam("selectorId");

    List<Entidad> entidades = repoEntidad.buscarPorLocalidad(localidadId);

    // Genero HTML con las opciones de los departamentos
    StringBuilder htmlOptions = new StringBuilder();

    for (Entidad entidad : entidades) {
      htmlOptions.append("<option value=\"").append(entidad.getId()).append("\">")
          .append(entidad.getNombre()).append("</option>");
    }

    context.contentType("text/html");
    context.result(htmlOptions.toString());
  }

  public void Establecimientos(Context context){
    EstablecimientoRepositorio repoEstablecimiento = new EstablecimientoRepositorio(entityManager);

    String entidadId = context.queryParam("selectorId");

    List<Establecimiento> establecimientos = repoEstablecimiento.buscarPorEntidad(entidadId);

    // Genero HTML con las opciones de los departamentos
    StringBuilder htmlOptions = new StringBuilder();

    for (Establecimiento establecimiento : establecimientos) {
      htmlOptions.append("<option value=\"").append(establecimiento.getId()).append("\">")
          .append(establecimiento.getNombre()).append("</option>");
    }

    context.contentType("text/html");
    context.result(htmlOptions.toString());

  }

  public void Incidentes(Context context){
    IncidenteRepositorio repoIncidente = new IncidenteRepositorio(entityManager);

    String localidadId = context.queryParam("selectorId");

    List<Incidente> incidentes = repoIncidente.buscarPorLocalidad(localidadId);
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
  }

  private String obtenerTextoServicioPrestado(ServicioPrestado servicioPrestado){
    return " - " + servicioPrestado.getServicio().getNombre();
  }

  public void Servicios(Context context){
    ServicioPrestadoRepositorio repoServicioPrestado = new ServicioPrestadoRepositorio(entityManager);

    String establecimientoId = context.queryParam("selectorId");

    List<ServicioPrestado> servicioPrestados = repoServicioPrestado.buscarPorEstablecimiento(establecimientoId);

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
  }
}
