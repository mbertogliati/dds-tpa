package ar.edu.utn.frba.dds.controllers.generales.servicios;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.servicios.Etiqueta;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.TipoEtiquetas;
import ar.edu.utn.frba.dds.repositorios.entidades.EstablecimientoRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.EtiquetaRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.TipoEtiquetaRepositorio;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

public class EtiquetasController implements ICrudViewsHandler {
  private EtiquetaRepositorio repoEtiquetas;
  private TipoEtiquetaRepositorio repoTipoEtiquetas;

  public EtiquetasController(EntityManager entityManager){
    this.repoEtiquetas = new EtiquetaRepositorio(entityManager);
    this.repoTipoEtiquetas = new TipoEtiquetaRepositorio(entityManager);
  }

  @Override
  public void index(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    model.put("etiquetas", this.repoEtiquetas.buscarTodas());
    model.put("tipoEtiquetas", this.repoTipoEtiquetas.buscarTodos());

    context.render("etiquetas.hbs", model);
  }

  @Override
  public void show(Context context) {
  }

  @Override
  public void create(Context context) {
  }

  @Override
  public void save(Context context) {
    int tipoId = Integer.parseInt(context.formParam("tipoId"));
    String valor = context.formParam("valorEtiqueta");

    TipoEtiquetas tipo = this.repoTipoEtiquetas.buscarPorId(tipoId);
    Etiqueta etiqueta = new Etiqueta(tipo, valor);

    this.repoEtiquetas.guardar(etiqueta);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Etiqueta creada correctamente."));
    context.redirect("/etiquetas?success");
  }

  @Override
  public void edit(Context context) {
  }

  @Override
  public void update(Context context) {
  }

  @Override
  public void delete(Context context) {
    int etiquetaId = Integer.parseInt(context.pathParam("id"));

    Etiqueta etiqueta = this.repoEtiquetas.buscarPorId(etiquetaId);
    this.repoEtiquetas.eliminar(etiqueta);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Etiqueta eliminada correctamente."));
    context.redirect("/etiquetas?success");
  }
}
