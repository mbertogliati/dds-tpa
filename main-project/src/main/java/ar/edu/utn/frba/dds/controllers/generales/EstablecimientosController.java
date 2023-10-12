package ar.edu.utn.frba.dds.controllers.generales;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.entidades.Denominacion;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.modelos.utilidades.Coordenada;
import ar.edu.utn.frba.dds.modelos.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EstablecimientoRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.DepartamentoRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.LocalidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.ProvinciaRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioPrestadoRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioRepositorio;
import io.javalin.http.Context;
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

    //BUSCO ESTABLECIMIENTO
    String idEstablecimiento = context.pathParam("id");
    Establecimiento establecimiento = this.repoEstablecimiento.buscarPorId(Integer.parseInt(idEstablecimiento));
    model.put("establecimiento", establecimiento);

    model.put("provincias", repoProvincia.buscarTodas());
    model.put("departamentos", establecimiento.getUbicacion().getMetadato().getProvincia().getDepartamentos());
    model.put("localidades", establecimiento.getUbicacion().getMetadato().getDepartamento().getLocalidades());

    model.put("editable", true);
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

    Float latitud = Float.valueOf(context.formParam("latitud"));
    Float longitud = Float.valueOf(context.formParam("longitud"));
    Coordenada coordenada = new Coordenada(latitud, longitud);
    establecimiento.setUbicacion(new Ubicacion(localidad.getDepartamento().getProvincia(), localidad.getDepartamento(), localidad, coordenada));

    Entidad entidad = repoEntidad.buscarPorId(Integer.parseInt(context.formParam("entidad")));
    entidad.agregarEstablecimiento(establecimiento);
    establecimiento.setEntidad(entidad);

    repoEntidad.actualizar(entidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Establecimiento agregado correctamente."));
    context.redirect("/entidades/"+entidad.getId()+"?success");
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
    Float latitud = Float.valueOf(context.formParam("latitud"));
    Float longitud = Float.valueOf(context.formParam("longitud"));
    Coordenada coordenada = new Coordenada(latitud, longitud);
    establecimiento.setUbicacion(new Ubicacion(localidad.getDepartamento().getProvincia(), localidad.getDepartamento(), localidad, coordenada));

    repoEstablecimiento.actualizar(establecimiento);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Establecimiento modificado correctamente."));
    context.redirect("/establecimientos/" + idEstablecimiento + "?success");
  }

  @Override
  public void delete(Context context) {
    Establecimiento establecimiento = repoEstablecimiento.buscarPorId(Integer.parseInt(context.pathParam("id")));

    repoEstablecimiento.eliminar(establecimiento);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Establecimiento eliminado correctamente."));
    context.redirect("/entidades/"+establecimiento.getEntidad().getId()+"?success");
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
      context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.ERROR, "No se puede eliminar el servicio porque tiene incidentes asociados."));
      context.redirect("/establecimientos/" + idEstablecimiento + "?error");
      return;
    }

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Servicio eliminado correctamente."));
    context.redirect("/establecimientos/" + idEstablecimiento + "?success");
  }

  public void agregarServicio(Context context){
    String idEstablecimiento = context.pathParam("id");
    Establecimiento establecimiento = repoEstablecimiento.buscarPorId(Integer.parseInt(idEstablecimiento));

    String idServicio = context.formParam("servicio");
    Servicio servicio = repoServicio.buscarPorId(Integer.parseInt(idServicio));

    establecimiento.agregarServicio(servicio);

    repoEstablecimiento.actualizar(establecimiento);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Servicio agregado correctamente."));
    context.redirect("/establecimientos/" + idEstablecimiento + "?success");
  }
}
