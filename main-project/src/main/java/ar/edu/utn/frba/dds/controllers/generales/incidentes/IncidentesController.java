package ar.edu.utn.frba.dds.controllers.generales.incidentes;

import ar.edu.utn.frba.dds.controllers.exceptions.FormInvalidoException;
import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidentePorComunidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidenteRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.ProvinciaRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioPrestadoRepositorio;
import io.javalin.http.Context;
import java.time.LocalDateTime;
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
  private IncidentePorComunidadRepositorio repoIncidenteComunidad;

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
    this.repoIncidenteComunidad = new IncidentePorComunidadRepositorio(entityManager);
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
    incidente.setFechaHoraApertura(LocalDateTime.now());

    incidente.agregarIncidenteComunidad();

    //PERSISTIR
    repoIncidente.guardar(incidente);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Incidente abierto correctamente."));
    context.redirect("/incidentes?success");
  }

  public void getAll(@NotNull Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    String respuestaHTML = "";
    try{
      respuestaHTML = ObtenedorListadoIncidentes.obtenerHTMLListadoIncidentes(model, context.formParam("estado"));

      model.put("contenidoIncidentes", respuestaHTML);

      context.render("base.hbs", model);
    }
    catch(Exception e){
      throw new FormInvalidoException("Error al obtener el listado de incidentes.");
    }
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

    //PERSISTIR
    this.repoIncidente.actualizar(incidente);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Incidente cerrado correctamente."));
    context.redirect("/incidentes?success");
  }
  private void obtenerVistaListadoIncidentes(@NotNull Context context){

  }
}
