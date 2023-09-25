package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.CreadorEntityManager;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EstablecimientoRepositorio;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidenteRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.DepartamentoRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.LocalidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.ProvinciaRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioPrestadoRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class AperturaIncidenteController implements Handler {
  ProvinciaRepositorio repositorio;
  DepartamentoRepositorio repoDepto;
  LocalidadRepositorio repoLocalidad;
  EntidadRepositorio repoEntidad;
  EstablecimientoRepositorio repoEst;
  ServicioPrestadoRepositorio repoServ;

  public AperturaIncidenteController(EntityManager entityManager){
    this.repositorio = new ProvinciaRepositorio(entityManager);
    this.repoDepto = new DepartamentoRepositorio(entityManager);
    this.repoLocalidad = new LocalidadRepositorio(entityManager);
    this.repoEntidad = new EntidadRepositorio(entityManager);
    this.repoEst = new EstablecimientoRepositorio(entityManager);
    this.repoServ = new ServicioPrestadoRepositorio(entityManager);
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    Map<String, Object> model = new HashMap<>();

    List<Provincia> provincias = repositorio.buscarTodas();
    List<Departamento> departamentos = repoDepto.buscarTodos();
    List<Localidad> localidades = repoLocalidad.obtenerTodas();
    List<Entidad> entidades = repoEntidad.buscarTodos();
    List<Establecimiento> establecimientos = repoEst.buscarTodos();
    List<ServicioPrestado> servicios = repoServ.buscarTodos();

    model.put("provincias", provincias);
    model.put("departamentos", departamentos);
    model.put("localidades", localidades);
    model.put("entidades", entidades);
    model.put("establecimientos", establecimientos);
    model.put("servicios", servicios);

    context.render("aperturaIncidente.hbs", model);
  }
}
