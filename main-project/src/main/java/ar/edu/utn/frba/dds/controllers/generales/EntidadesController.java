package ar.edu.utn.frba.dds.controllers.generales;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.entidades.Denominacion;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.modelos.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadPrestadoraRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.DepartamentoRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.LocalidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.ProvinciaRepositorio;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class EntidadesController implements ICrudViewsHandler{
  private EntidadRepositorio repoEntidad;
  private EntidadPrestadoraRepositorio repoEntidadPrestadora;
  private ProvinciaRepositorio repoProvincia;
  private DepartamentoRepositorio repoDepartamento;
  private LocalidadRepositorio repoLocalidad;

  public EntidadesController(EntityManager entityManager){
    this.repoEntidad = new EntidadRepositorio(entityManager);
    this.repoProvincia = new ProvinciaRepositorio(entityManager);
    this.repoLocalidad = new LocalidadRepositorio(entityManager);
    this.repoDepartamento = new DepartamentoRepositorio(entityManager);
    this.repoEntidadPrestadora = new EntidadPrestadoraRepositorio(entityManager);
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

    EntidadPrestadora entidadPrestadora = repoEntidadPrestadora.buscarPorId(Integer.parseInt(context.pathParam("idEntidadPrestadora")));
    model.put("entidadPrestadora", entidadPrestadora);
    model.put("editable", true);
    model.put("creacion", true);
    model.put("provinciasGen", repoProvincia.buscarTodas());

    context.render("verEntidad.hbs", model);
  }

  @Override
  public void save(Context context) {
    Entidad entidad = new Entidad();

    entidad.setNombre(context.formParam("nombreEntidad"));
    entidad.setDenominacion(new Denominacion(context.formParam("denominacion")));
    Localidad localidad = repoLocalidad.obtenerLocalidadPorId(context.formParam("localidad"));
    entidad.setUbicacion(new Ubicacion(localidad.getDepartamento().getProvincia(), localidad.getDepartamento(), localidad));

    EntidadPrestadora entidadPrestadora = repoEntidadPrestadora.buscarPorId(Integer.parseInt(context.formParam("entidadPrestadora")));
    entidadPrestadora.agregarEntidad(entidad);

    repoEntidadPrestadora.actualizar(entidadPrestadora);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Entidad agregada correctamente."));
    context.redirect("/entidadesPrestadoras?success");
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    String idEntidad = context.pathParam("id");
    Entidad entidad = this.repoEntidad.buscarPorId(Integer.parseInt(idEntidad));
    model.put("entidad", entidad);
    model.put("establecimientos", entidad.getEstablecimientos());

    model.put("provincias", repoProvincia.buscarTodas());
    model.put("departamentos", entidad.getUbicacion().getMetadato().getProvincia().getDepartamentos());
    model.put("localidades", entidad.getUbicacion().getMetadato().getDepartamento().getLocalidades());

    model.put("editable", true);

    context.render("verEntidad.hbs", model);
  }

  @Override
  public void update(Context context) {
    String idEntidad = context.pathParam("id");
    Entidad entidad = this.repoEntidad.buscarPorId(Integer.parseInt(idEntidad));

    entidad.setNombre(context.formParam("nombreEntidad"));
    entidad.setDenominacion(new Denominacion(context.formParam("denominacion")));

    Localidad localidad = repoLocalidad.obtenerLocalidadPorId(context.formParam("localidad"));
    entidad.setUbicacion(new Ubicacion(localidad.getDepartamento().getProvincia(), localidad.getDepartamento(), localidad));

    repoEntidad.actualizar(entidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Entidad modificada correctamente."));
    context.redirect("/entidades/" + idEntidad + "?success");
  }

  @Override
  public void delete(Context context) {

  }

  public void sacarEstablecimiento(Context context){
    Entidad entidad = repoEntidad.buscarPorId(Integer.parseInt(context.pathParam("id")));

    entidad.eliminarEstablecimientoPorID(Integer.parseInt(context.pathParam("idEstablecimiento")));

    repoEntidad.actualizar(entidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Establecimiento eliminado correctamente."));
    context.redirect("/entidades/"+entidad.getId()+"?success");
  }
}
