package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.modelos.entidades.Denominacion;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.modelos.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EstablecimientoRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.DepartamentoRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.LocalidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.ProvinciaRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioPrestadoRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioRepositorio;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

public class EstablecimientosController implements ICrudViewsHandler {
  private ServicioRepositorio repoServicio;
  private EstablecimientoRepositorio repoEstablecimiento;
  private ProvinciaRepositorio repoProvincia;
  private DepartamentoRepositorio repoDepartamento;
  private LocalidadRepositorio repoLocalidad;
  private ServicioPrestadoRepositorio repoServicioPrestado;
  private EntidadRepositorio repoEntidad;

  public EstablecimientosController(EntityManager entityManager){
    this.repoServicio = new ServicioRepositorio(entityManager);
    this.repoDepartamento = new DepartamentoRepositorio(entityManager);
    this.repoProvincia = new ProvinciaRepositorio(entityManager);
    this.repoEstablecimiento = new EstablecimientoRepositorio(entityManager);
    this.repoLocalidad = new LocalidadRepositorio(entityManager);
    this.repoServicioPrestado = new ServicioPrestadoRepositorio(entityManager);
    this.repoEntidad = new EntidadRepositorio(entityManager);
  }

  @Override
  public void index(Context context) {

  }

  @Override
  public void show(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    String eliminado = context.queryParam("servicioEliminado");
    if(eliminado != null){
      Servicio servicioEliminado = repoServicio.buscarPorId(Integer.parseInt(eliminado));
      model.put("servicioEliminado", servicioEliminado);
    }

    String agregado = context.queryParam("servicioAgregado");
    if(agregado != null){
      Servicio servicioAgregado = repoServicio.buscarPorId(Integer.parseInt(agregado));
      model.put("servicioAgregado", servicioAgregado);
    }

    String success = context.queryParam("success");
    if(success != null){
      model.put("success", success);
    }

    String error = context.queryParam("error");
    if(error != null){
      model.put("error", error);
    }

    //BUSCO ESTABLECIMIENTO
    String idEstablecimiento = context.pathParam("id");
    Establecimiento establecimiento = this.repoEstablecimiento.buscarPorId(Integer.parseInt(idEstablecimiento));
    model.put("establecimiento", establecimiento);

    //BUSCO PROVINCIAS
    StringBuilder stringBuilder = new StringBuilder("");
    List<Provincia> provincias = repoProvincia.buscarTodas();
    for (Provincia provincia : provincias){
      if(provincia.getId().equals(establecimiento.getUbicacion().getMetadato().getProvincia().getId())){
        stringBuilder.append("<option value=\"" + provincia.getId() + "\" selected>" + provincia.getNombre() + "</option>");
      }else{
        stringBuilder.append("<option value=\"" + provincia.getId() + "\">" + provincia.getNombre() + "</option>");
      }
    }
    model.put("provincias", stringBuilder.toString());

    //BUSCO DEPARTAMENTOS
    stringBuilder = new StringBuilder("");
    List<Departamento> departamentos = repoDepartamento.buscarPorProvincia(establecimiento.getUbicacion().getMetadato().getProvincia().getId());
    for (Departamento departamento : departamentos){
      if(departamento.getId().equals(establecimiento.getUbicacion().getMetadato().getDepartamento().getId())){
        stringBuilder.append("<option value=\"" + departamento.getId() + "\" selected>" + departamento.getNombre() + "</option>");
      }else{
        stringBuilder.append("<option value=\"" + departamento.getId() + "\">" + departamento.getNombre() + "</option>");
      }
    }
    model.put("departamentos", stringBuilder.toString());

    //BUSCO LOCALIDADES
    stringBuilder = new StringBuilder("");
    List<Localidad> localidades = repoLocalidad.buscarPorDepartamento(establecimiento.getUbicacion().getMetadato().getDepartamento().getId());
    for (Localidad localidad : localidades){
      if(localidad.getId().equals(establecimiento.getUbicacion().getMetadato().getLocalidad().getId())){
        stringBuilder.append("<option value=\"" + localidad.getId() + "\" selected>" + localidad.getNombre() + "</option>");
      }else{
        stringBuilder.append("<option value=\"" + localidad.getId() + "\">" + localidad.getNombre() + "</option>");
      }
    }

    model.put("editable", true);
    model.put("localidades", stringBuilder.toString());
    model.put("serviciosPrestados", establecimiento.getServiciosPrestados());
    model.put("serviciosGenerales", repoServicio.buscarTodos());
    context.render("verEstablecimiento.hbs", model);
  }

  @Override
  public void create(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    Entidad entidad = repoEntidad.buscarPorId(Integer.parseInt(context.pathParam("idEntidad")));
    model.put("entidad", entidad);
    model.put("editable", true);
    model.put("creacion", true);
    model.put("provinciasGen", repoProvincia.buscarTodas());

    context.render("verEstablecimiento.hbs", model);
  }

  @Override
  public void save(Context context) {
    Establecimiento establecimiento = new Establecimiento();

    establecimiento.setNombre(context.formParam("nombreEstablecimiento"));
    establecimiento.setDenominacion(new Denominacion(context.formParam("denominacion")));
    Localidad localidad = repoLocalidad.obtenerLocalidadPorId(context.formParam("localidad"));
    establecimiento.setUbicacion(new Ubicacion(localidad.getDepartamento().getProvincia(), localidad.getDepartamento(), localidad));

    Entidad entidad = repoEntidad.buscarPorId(Integer.parseInt(context.formParam("entidad")));
    entidad.agregarEstablecimiento(establecimiento);

    repoEntidad.actualizar(entidad);

    context.redirect("/entidades/"+entidad.getId()+"?result=successAddEstablecimiento");
  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) {
    String idEstablecimiento = context.pathParam("id");
    Establecimiento establecimiento = this.repoEstablecimiento.buscarPorId(Integer.parseInt(idEstablecimiento));

    establecimiento.setNombre(context.formParam("nombreEstablecimiento"));
    establecimiento.setDenominacion(new Denominacion(context.formParam("denominacion")));

    Localidad localidad = repoLocalidad.obtenerLocalidadPorId(context.formParam("localidad"));
    establecimiento.setUbicacion(new Ubicacion(localidad.getDepartamento().getProvincia(), localidad.getDepartamento(), localidad));

    repoEstablecimiento.actualizar(establecimiento);

    context.redirect("/establecimientos/" + idEstablecimiento + "?success=true");
  }

  @Override
  public void delete(Context context) {

  }

  public void sacarServicio(Context context){
    String idEstablecimiento = context.pathParam("id");
    Establecimiento establecimiento = repoEstablecimiento.buscarPorId(Integer.parseInt(idEstablecimiento));

    String idServicioPrestado = context.pathParam("idServicioPrestado");
    ServicioPrestado servicioPrestado = repoServicioPrestado.buscarPorId(Integer.parseInt(idServicioPrestado));

    establecimiento.eliminarServicioPrestado(servicioPrestado);

    try {
      repoServicioPrestado.eliminar(servicioPrestado);
      repoEstablecimiento.actualizar(establecimiento);
    }catch (Exception e){
      context.redirect("/establecimientos/" + idEstablecimiento + "?error=FK");
      return;
    }

    context.redirect("/establecimientos/" + idEstablecimiento + "?servicioEliminado=" + servicioPrestado.getServicio().getId());
  }

  public void agregarServicio(Context context){
    String idEstablecimiento = context.pathParam("id");
    Establecimiento establecimiento = repoEstablecimiento.buscarPorId(Integer.parseInt(idEstablecimiento));

    String idServicio = context.formParam("servicio");
    Servicio servicio = repoServicio.buscarPorId(Integer.parseInt(idServicio));

    establecimiento.agregarServicio(servicio);

    repoEstablecimiento.actualizar(establecimiento);

    context.redirect("/establecimientos/" + idEstablecimiento + "?servicioAgregado=" + servicio.getId());
  }
}
