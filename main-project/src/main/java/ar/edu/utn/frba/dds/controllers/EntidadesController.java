package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
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

    context.redirect("/entidadesPrestadoras?result=successAddEntidad");
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    String param = context.queryParam("success");
    if(param != null){
      model.put("success", true);
    }

    String idEntidad = context.pathParam("id");
    Entidad entidad = this.repoEntidad.buscarPorId(Integer.parseInt(idEntidad));
    model.put("entidad", entidad);


    StringBuilder stringBuilder = new StringBuilder("");
    List<Provincia> provincias = repoProvincia.buscarTodas();
    //TODO: Rompe el Single responsability principle.
    for (Provincia provincia : provincias){
      if(provincia.getId().equals(entidad.getUbicacion().getMetadato().getProvincia().getId())){
        stringBuilder.append("<option value=\"" + provincia.getId() + "\" selected>" + provincia.getNombre() + "</option>");
      }else{
        stringBuilder.append("<option value=\"" + provincia.getId() + "\">" + provincia.getNombre() + "</option>");
      }
    }
    model.put("provincias", stringBuilder.toString());


    stringBuilder = new StringBuilder("");
    List<Departamento> departamentos = repoDepartamento.buscarPorProvincia(entidad.getUbicacion().getMetadato().getProvincia().getId());
    for (Departamento departamento : departamentos){
      if(departamento.getId().equals(entidad.getUbicacion().getMetadato().getDepartamento().getId())){
        stringBuilder.append("<option value=\"" + departamento.getId() + "\" selected>" + departamento.getNombre() + "</option>");
      }else{
        stringBuilder.append("<option value=\"" + departamento.getId() + "\">" + departamento.getNombre() + "</option>");
      }
    }
    model.put("departamentos", stringBuilder.toString());


    stringBuilder = new StringBuilder("");
    List<Localidad> localidades = repoLocalidad.buscarPorDepartamento(entidad.getUbicacion().getMetadato().getDepartamento().getId());
    for (Localidad localidad : localidades){
      if(localidad.getId().equals(entidad.getUbicacion().getMetadato().getLocalidad().getId())){
        stringBuilder.append("<option value=\"" + localidad.getId() + "\" selected>" + localidad.getNombre() + "</option>");
      }else{
        stringBuilder.append("<option value=\"" + localidad.getId() + "\">" + localidad.getNombre() + "</option>");
      }
    }
    model.put("localidades", stringBuilder.toString());

    model.put("establecimientos", entidad.getEstablecimientos());

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

    context.redirect("/entidades/" + idEntidad + "?success=true");
  }

  @Override
  public void delete(Context context) {

  }

  public void sacarEstablecimiento(Context context){
    Entidad entidad = repoEntidad.buscarPorId(Integer.parseInt(context.pathParam("id")));

    entidad.eliminarEstablecimientoPorID(Integer.parseInt(context.pathParam("idEstablecimiento")));

    repoEntidad.actualizar(entidad);

    context.redirect("/entidades/"+entidad.getId()+"?result=successSacarEstablecimiento");
  }
}
