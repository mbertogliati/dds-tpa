package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidenteRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.DepartamentoRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.LocalidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.ProvinciaRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class CierreIncidenteController implements Handler {
  ProvinciaRepositorio repoProvincia;
  DepartamentoRepositorio repoDepartamento;
  LocalidadRepositorio repoLocalidad;
  IncidenteRepositorio repoIncidente;

  public CierreIncidenteController(EntityManager entityManager){
    this.repoProvincia = new ProvinciaRepositorio(entityManager);
    this.repoDepartamento = new DepartamentoRepositorio(entityManager);
    this.repoLocalidad = new LocalidadRepositorio(entityManager);
    this.repoIncidente = new IncidenteRepositorio(entityManager);
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    if(VerificadorLogueo.noEstaLogueado(context.sessionAttribute("logueado"))){
      context.redirect("/login");
      return;
    }

    Map<String, Object> model = new HashMap<>();

    List<Provincia> provincias = repoProvincia.buscarTodas();
    List<Departamento> departamentos = repoDepartamento.buscarTodos();
    List<Localidad> localidades = repoLocalidad.obtenerTodas();
    List<Incidente> incidentes = repoIncidente.buscarTodos();

    model.put("provincias", provincias);
    model.put("departamentos", departamentos);
    model.put("localidades", localidades);
    model.put("incidentes", incidentes);

    context.render("cierreIncidente.hbs", model);
  }
}
