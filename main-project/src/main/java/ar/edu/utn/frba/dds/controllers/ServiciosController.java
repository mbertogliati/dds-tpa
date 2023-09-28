package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.modelos.servicios.Etiqueta;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioRepositorio;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

public class ServiciosController implements ICrudViewsHandler {
  ServicioRepositorio repoServicios;

  public ServiciosController(EntityManager entityManager){
    this.repoServicios = new ServicioRepositorio(entityManager);
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

    if(context.queryParam("success") != null){
      model.put("success", true);
    }

    context.render("crearServicio.hbs", model);
  }

  @Override
  public void save(Context context) {
    //NOMBRE
    String nombreServicio = context.formParam("nombre");

    //ETIQUETAS
    List<String> tipos = context.formParams("tipo[]");
    List<String> valores = context.formParams("valor[]");

    //Creo etiquetas
    List<Etiqueta> etiquetas = new ArrayList<>();
    for (int i = 0; i < tipos.size(); i++) {
      Etiqueta etiquetaCreada = new Etiqueta();
      etiquetaCreada.setTipo(tipos.get(i));
      etiquetaCreada.setValor(valores.get(i));
      etiquetas.add(etiquetaCreada);
    }

    //CREO SERVICIO
    Servicio servicio = new Servicio();
    servicio.setNombre(nombreServicio);
    servicio.setEtiquetas(etiquetas);

    //GUARDO SERVICIO
    repoServicios.guardar(servicio);

    context.redirect("/servicios?success=true");
  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) {

  }

  @Override
  public void delete(Context context) {

  }
}
