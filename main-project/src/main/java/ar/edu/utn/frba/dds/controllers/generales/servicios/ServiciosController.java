package ar.edu.utn.frba.dds.controllers.generales.servicios;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.servicios.Etiqueta;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.modelos.servicios.TipoEtiquetas;
import ar.edu.utn.frba.dds.repositorios.entidades.EstablecimientoRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.EtiquetaRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.TipoEtiquetaRepositorio;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

public class ServiciosController implements ICrudViewsHandler {
  ServicioRepositorio repoServicios;
  EstablecimientoRepositorio repoEstablecimiento;
  EtiquetaRepositorio repoEtiquetas;
  TipoEtiquetaRepositorio repoTipos;

  public ServiciosController(){
    this.repoServicios = new ServicioRepositorio();
    this.repoEstablecimiento = new EstablecimientoRepositorio();
    this.repoEtiquetas = new EtiquetaRepositorio();
    this.repoTipos = new TipoEtiquetaRepositorio();
  }

  @Override
  public void index(Context context) {

  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    List<TipoEtiquetas> tipos = this.repoTipos.buscarTodos();
    model.put("tipoEtiquetas", tipos);

    context.render("crearServicio.hbs", model);
  }

  public void createFromEstablecimiento(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    List<TipoEtiquetas> tipos = this.repoTipos.buscarTodos();
    model.put("tipoEtiquetas", tipos);

    Establecimiento establecimiento = repoEstablecimiento.buscarPorId(Integer.parseInt(context.pathParam("idEstablecimiento")));

    if(establecimiento != null){
      model.put("establecimiento", establecimiento);
    }

    context.render("crearServicio.hbs", model);
  }

  @Override
  public void save(Context context) {
    //NOMBRE
    String nombreServicio = context.formParam("nombre");

    //ETIQUETAS
    List<String> etiquetasId = context.formParams("etiqueta[]");

    //Creo etiquetas
    List<Etiqueta> etiquetas = new ArrayList<>();
    for (int i = 0; i < etiquetasId.size(); i++) {
      Etiqueta etiqueta = this.repoEtiquetas.buscarPorId(Integer.parseInt(etiquetasId.get(i)));
      etiquetas.add(etiqueta);
    }

    //CREO SERVICIO
    Servicio servicio = new Servicio();
    servicio.setNombre(nombreServicio);
    servicio.setEtiquetas(etiquetas);

    //GUARDO SERVICIO
    repoServicios.guardar(servicio);

    String idEstablecimiento = context.formParam("establecimiento");
    if(idEstablecimiento != null){
      context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Servicio agregado correctamente."));
      context.redirect("/establecimientos/"+idEstablecimiento+"?success");
    }else{
      context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Servicio creado correctamente."));
      context.redirect("/servicios?success");
    }
  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) {

  }

  @Override
  public void delete(Context context) {
    Servicio servicio = repoServicios.buscarPorId(Integer.parseInt(context.pathParam("id")));

    repoServicios.eliminar(servicio);

    context.sessionAttribute("msg",new MensajeVista(MensajeVista.TipoMensaje.SUCCESS,"Servicio eliminado correctamente"));
    context.redirect("/servicios");
  }
}
