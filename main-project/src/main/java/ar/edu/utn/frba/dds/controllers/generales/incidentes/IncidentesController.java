package ar.edu.utn.frba.dds.controllers.generales.incidentes;

import ar.edu.utn.frba.dds.controllers.exceptions.ExternalException;
import ar.edu.utn.frba.dds.controllers.exceptions.FormInvalidoException;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.FiltradorPorComunidad;
import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Membresia;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    List<Comunidad> comunidades = persona.getMembresias().stream().map(Membresia::getComunidad).filter(c -> c.getServiciosPrestados().stream().map(s -> s.getId()).toList().containsAll(incidente.getServiciosAfectados().stream().map(sp -> sp.getId()).toList())).toList();
    comunidades.forEach(incidente::agregarIncidenteComunidad);

    //PERSISTIR
    repoIncidente.guardar(incidente);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Incidente abierto correctamente."));
    context.redirect("/incidentes?success");
  }

  public void getAll(@NotNull Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    String respuestaHTML = "";
    try{
      respuestaHTML = ObtenedorListadoIncidentes.obtenerHTMLListadoIncidentes(model, context.queryParam("estado"));

      if(Objects.equals(respuestaHTML, "paraRevision")){
        Comunidad comunidad = context.sessionAttribute("comunidad");
        Persona persona = ((Usuario) context.sessionAttribute("usuario")).getPersonaAsociada();
        List<IncidentePorComunidad> incidentesPorComunidad = repoIncidenteComunidad.incidentesEnRevision(persona, comunidad);

        model.put("incidentesPorComunidad", incidentesPorComunidad);
        context.render("listaIncidentes.hbs", model);
      }else{
        model.put("contenidoIncidentes", respuestaHTML);
        context.render("base.hbs", model);
      }
    }
    catch(Exception e){
      throw new ExternalException("Error al obtener el listado de incidentes.");
    }
  }

  public void vistaApertura(@NotNull Context context){
      Map<String, Object> model = GeneradorModel.model(context);

      Comunidad comunidad = (Comunidad) model.get("comunidad");

      List<Provincia> provincias = FiltradorPorComunidad.provinciasDeComunidad(comunidad , repoProvincia.buscarTodas());
      model.put("provincias", provincias);

      context.render("aperturaIncidente.hbs", model);
  }

  public void vistaCierre(@NotNull Context context){
    Map<String, Object> model = GeneradorModel.model(context);

    Comunidad comunidad = (Comunidad) model.get("comunidad");
    List<Provincia> provincias = FiltradorPorComunidad.provinciasConIncidentes(comunidad, repoProvincia.buscarTodas());

    model.put("provincias", provincias);

    context.render("cierreIncidente.hbs", model);
  }

  public void cerrar(@NotNull Context context){
    //INCIDENTE
    String idIncidente = context.formParam("incidente");
    Incidente incidente = this.repoIncidente.buscarPorId(Integer.parseInt(idIncidente));

    //PERSONA
    Usuario usuario = context.sessionAttribute("usuario");

    Comunidad comunidad = context.sessionAttribute("comunidad");

    //CERRAR INCIDENTE
    usuario.getPersonaAsociada().cerrarIncidente(incidente, comunidad);

    //PERSISTIR
    this.repoIncidente.actualizar(incidente);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Incidente cerrado correctamente."));
    context.redirect("/incidentes?success");
  }
  private void obtenerVistaListadoIncidentes(@NotNull Context context){

  }
}
