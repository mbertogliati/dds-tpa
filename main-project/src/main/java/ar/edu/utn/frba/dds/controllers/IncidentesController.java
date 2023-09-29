package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidentePorComunidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidenteRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.ProvinciaRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioPrestadoRepositorio;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class IncidentesController {
  private IncidenteRepositorio repositorio;
  private IncidentePorComunidadRepositorio repoComunidad;
  private ProvinciaRepositorio repoProvincia;
  private ServicioPrestadoRepositorio repoServicioPrestado;
  private IncidenteRepositorio repoIncidente;

  private Integer obtenerIdServicioPrestado(String texto){
    int indiceEspacio = texto.indexOf(" ");
    return Integer.parseInt(texto.substring(0, indiceEspacio));

  }


  public IncidentesController(EntityManager entityManager){
    this.repositorio = new IncidenteRepositorio(entityManager);
    this.repoComunidad = new IncidentePorComunidadRepositorio(entityManager);
    this.repoProvincia = new ProvinciaRepositorio(entityManager);
    this.repoServicioPrestado = new ServicioPrestadoRepositorio(entityManager);
    this.repoIncidente = new IncidenteRepositorio(entityManager);
  }

  public void handle(@NotNull Context context) throws Exception {


  }

  public void abrir(@NotNull Context context) {
    //INCIDENTE
    Incidente incidente = new Incidente();

    //OBSERVACIONES
    String observaciones = context.formParam("observaciones");
    incidente.setObservaciones(observaciones);

    //SERVICIOS
    List<String> servicios = context.formParams("servicio[]");
    for (int i = 0; i < servicios.size(); i++) {
      ServicioPrestado servicioPrestado = repoServicioPrestado.buscarPorId(obtenerIdServicioPrestado(servicios.get(i)));
      incidente.agregarServiciosPrestados(servicioPrestado);
    }

    //PERSONA
    Usuario usuario = context.sessionAttribute("usuario");
    Persona persona = usuario.getPersonaAsociada();
    incidente.setAutorApertura(persona);

    incidente.agregarIncidenteComunidad();

    //PERSISTIR
    repoIncidente.guardar(incidente);

    context.redirect("/incidentes?success=abierto");
  }

  public void getAll(@NotNull Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    String paramSuccess = context.queryParam("success");

    if(paramSuccess != null){
      model.put("success", paramSuccess);
    }

    List<Incidente> listaIncidentes;
    String paramEstado = context.queryParam("estado");


    if(paramEstado != null){
      Usuario usuario = context.sessionAttribute("usuario");
      listaIncidentes = repositorio.incidentesDeEstado(paramEstado, usuario.getPersonaAsociada().getId());
    }else{
      listaIncidentes = repositorio.buscarTodos();
    }

    List<IncidentePorComunidad> incidentesPorComunidad = repoComunidad.buscarTodos();
    listaIncidentes.forEach(i -> i.actualizarEstado(incidentesPorComunidad));



    model.put("incidentes", listaIncidentes);

    context.render("listaIncidentes.hbs", model);
  }

  public void vistaApertura(@NotNull Context context){

      Map<String, Object> model = GeneradorModel.model(context);

      List<Provincia> provincias = repoProvincia.buscarTodas();

      model.put("provincias", provincias);


      context.render("aperturaIncidente.hbs", model);
  }
  public void vistaCierre(@NotNull Context context){
    Map<String, Object> model = GeneradorModel.model(context);



    List<Provincia> provincias = repoProvincia.buscarTodas();

    model.put("provincias", provincias);

    context.render("cierreIncidente.hbs", model);
  }
  public void cerrar(@NotNull Context context){
    //INCIDENTE
    String idIncidente = context.formParam("incidente");
    Incidente incidente = this.repoIncidente.buscarPorId(Integer.parseInt(idIncidente));

    //PERSONA
    Usuario usuario = context.sessionAttribute("usuario");

    //CERRAR INCIDENTE
    usuario.getPersonaAsociada().cerrarIncidente(incidente);

    //TODO: VER COMO HACEMOS PARA QUE EL INCIDENTE APAREZCA COMO CERRADO PARA MÍ SI SOY DE UNA COMUNIDAD QUE ESTA CERRADO, PERO ABIERTO PARA OTRA PERSONA QUE PERTENECE A UNA COMUNIDAD QUE ESTÁ ABIERTO

    //PERSISTIR
    this.repoIncidente.actualizar(incidente);

    context.redirect("/incidentes?success=cerrado");
  }
}
