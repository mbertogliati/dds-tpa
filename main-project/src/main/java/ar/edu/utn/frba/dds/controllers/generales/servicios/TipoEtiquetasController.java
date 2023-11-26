package ar.edu.utn.frba.dds.controllers.generales.servicios;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.servicios.Etiqueta;
import ar.edu.utn.frba.dds.modelos.servicios.TipoEtiquetas;
import ar.edu.utn.frba.dds.repositorios.servicios.EtiquetaRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.TipoEtiquetaRepositorio;
import io.javalin.http.Context;
import java.util.Map;
import javax.persistence.EntityManager;

public class TipoEtiquetasController implements ICrudViewsHandler {
  private TipoEtiquetaRepositorio repoTipoEtiquetas;

  public TipoEtiquetasController(EntityManager entityManager){
    this.repoTipoEtiquetas = new TipoEtiquetaRepositorio(entityManager);
  }

  @Override
  public void index(Context context) {
  }

  @Override
  public void show(Context context) {
  }

  @Override
  public void create(Context context) {
  }

  @Override
  public void save(Context context) {
    String tipoNombre = context.formParam("tipoNombre");

    TipoEtiquetas tipo = new TipoEtiquetas(tipoNombre);
    this.repoTipoEtiquetas.guardar(tipo);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Tipo de etiqueta creado correctamente."));
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
  }
}
