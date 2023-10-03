package ar.edu.utn.frba.dds.controllers.generales;

import static ar.edu.utn.frba.dds.controllers.generales.VerificadorRol.Permiso.ADMINISTRAR_COMUNIDAD;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Membresia;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.repositorios.comunidades.ComunidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.MembresiaRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.RolRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioRepositorio;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;

public class ComunidadesController implements ICrudViewsHandler {
  private ComunidadRepositorio repoComunidad;
  private UsuarioRepositorio repoUsuario;
  private ServicioRepositorio repoServicio;
  private MembresiaRepositorio repoMembresia;
  private RolRepositorio repoRol;

  public ComunidadesController(EntityManager entityManager){
    this.repoComunidad = new ComunidadRepositorio(entityManager);
    this.repoUsuario = new UsuarioRepositorio(entityManager);
    this.repoServicio = new ServicioRepositorio(entityManager);
    this.repoMembresia = new MembresiaRepositorio(entityManager);
    this.repoRol = new RolRepositorio(entityManager);
  }

  public void deUsuario(Context context){
    Map<String, Object> model = GeneradorModel.model(context);

    Usuario usuario = context.sessionAttribute("usuario");

    List<Comunidad> comunidadesDeUsuario = usuario.getPersonaAsociada().getMembresias().stream().map(m -> m.getComunidad()).toList();

    model.put("comunidades", obtenerComunidadesConUsuarioActual(comunidadesDeUsuario, usuario));

    context.render("comunidades.hbs", model);
  }

  public void cambiarRol(Context context){
    Map<String, Object> model = GeneradorModel.model(context);

    Usuario usuario = repoUsuario.buscarPorId(Integer.parseInt(context.pathParam("idUsuario")));
    Servicio servicio = repoServicio.buscarPorId(Integer.parseInt(context.pathParam("idServicio")));
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("id")));

    if(context.pathParam("nuevoRol").equals("afectado")){
      comunidad.getMembresia(usuario.getPersonaAsociada()).agregarServicioAfectado(servicio);
    }else{
      comunidad.getMembresia(usuario.getPersonaAsociada()).agregarServicioObservado(servicio);
    }

    repoComunidad.actualizarComunidad(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Rol cambiado correctamente."));
    context.redirect("/comunidades/"+comunidad.getId()+"?success");
  }

  @Override
  public void index(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    model.put("comunidades", obtenerComunidadesConUsuarioActual(repoComunidad.obtenerTodas(), context.sessionAttribute("usuario")));

    context.render("comunidades.hbs", model);
  }

  @Override
  public void show(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    Usuario usuario = context.sessionAttribute("usuario");
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("id")));

    if(VerificadorRol.tieneRol(usuario, comunidad, ADMINISTRAR_COMUNIDAD)){
      model.put("adminComunidad", true);
    }

    model.put("comunidad", new ComunidadConUsuarioActual(comunidad,usuario.getPersonaAsociada()));

    context.render("verComunidad.hbs", model);
  }

  @Override
  public void create(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    Comunidad comunidadVacia = new Comunidad();
    model.put("comunidad", comunidadVacia);
    model.put("editable", true);
    model.put("creacion", true);
    model.put("serviciosGenerales", repoServicio.buscarTodos());

    context.render("verComunidad.hbs", model);
  }

  @Override
  public void save(Context context) {
    Comunidad comunidad = new Comunidad();

    comunidad.setNombre(context.formParam("nombre"));
    comunidad.setDetalle(context.formParam("detalle"));

    List<String> idServicios = context.formParams("servicios[]");
    List<Servicio> servicios = new ArrayList<>();
    for (String id : idServicios){
      servicios.add(repoServicio.buscarPorId(Integer.parseInt(id)));
    }
    comunidad.setServicios(servicios);

    repoComunidad.guardarComunidad(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Comunidad creada correctamente."));
    context.redirect("/comunidades/"+comunidad.getId()+"/edit?success");
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    Usuario usuario = context.sessionAttribute("usuario");
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("id")));

    if(VerificadorRol.tieneRol(usuario, comunidad, ADMINISTRAR_COMUNIDAD)){
      model.put("editable", true);
      model.put("edicion", true);
      model.put("adminComunidad", true);
    }

    model.put("comunidad", new ComunidadConUsuarioActual(comunidad,usuario.getPersonaAsociada()));
    model.put("serviciosGenerales", repoServicio.buscarTodos());

    context.render("verComunidad.hbs", model);
  }

  @Override
  public void update(Context context) {
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("id")));

    comunidad.setNombre(context.formParam("nombre"));
    comunidad.setDetalle(context.formParam("detalle"));

    repoComunidad.actualizarComunidad(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Comunidad modificada correctamente."));
    context.redirect("/comunidades/"+comunidad.getId()+"/edit?success");
  }

  @Override
  public void delete(Context context) {

  }

  public void sacarMiembro(Context context){
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("id")));

    Usuario usuario = repoUsuario.buscarPorId(Integer.parseInt(context.pathParam("idUsuario")));

    Membresia membresia = comunidad.getMembresias().stream().filter(m -> m.getPersona().getId() == usuario.getPersonaAsociada().getId()).toList().get(0);

    comunidad.eliminarMembresia(membresia);

    repoUsuario.actualizar(usuario);
    repoComunidad.actualizarComunidad(comunidad);
    repoMembresia.eliminar(membresia);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Membresia eliminada correctamente."));
    context.redirect("/comunidades?success");
  }

  public void unirMiembro(Context context){
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("id")));

    Usuario usuario = repoUsuario.buscarPorId(Integer.parseInt(context.pathParam("idUsuario")));

    comunidad.agregarPersona(usuario.getPersonaAsociada(), repoRol.rolDefaultComunidad());

    repoUsuario.actualizar(usuario);
    repoComunidad.actualizarComunidad(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Membresia agregada correctamente."));
    context.redirect("/comunidades/?success");
  }

  public void agregarServicio(Context context){
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("id")));

    Servicio servicio = repoServicio.buscarPorId(Integer.parseInt(context.formParam("servicio")));

    comunidad.agregarServicio(servicio);

    repoComunidad.actualizarComunidad(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Servicio agregado correctamente."));
    context.redirect("/comunidades/"+comunidad.getId()+"/edit?success");
  }

  public void quitarServicio(Context context){
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("id")));

    Servicio servicio = repoServicio.buscarPorId(Integer.parseInt(context.pathParam("idServicio")));

    comunidad.eliminarServicio(servicio);

    repoComunidad.actualizarComunidad(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Servicio eliminado correctamente."));
    context.redirect("/comunidades/"+comunidad.getId()+"/edit?success");
  }
  private List<ComunidadConUsuarioActual> obtenerComunidadesConUsuarioActual(List<Comunidad> comunidades, Usuario usuario){
    return comunidades.stream().map(c->new ComunidadConUsuarioActual(c,usuario.getPersonaAsociada())).toList();
  }

  @Getter
  @Setter
  public class ComunidadConUsuarioActual{
    private int id;
    private String nombre;
    private String detalle;
    private List<ServicioEsAfectado> servicios;
    private Boolean tieneAUsuarioActual;

    public ComunidadConUsuarioActual(Comunidad comunidad, Persona persona){
      this.nombre = comunidad.getNombre();
      this.detalle = comunidad.getDetalle();
      this.servicios = new ArrayList<>();
      for (Servicio servicio : comunidad.getServicios()){
        this.servicios.add(new ServicioEsAfectado(servicio, comunidad.getMembresia(persona)));
      }
      this.id = comunidad.getId();
      this.tieneAUsuarioActual = comunidad.getMembresias().stream().anyMatch(m -> m.getPersona().getId() == persona.getId());
    }
  }

  @Getter
  @Setter
  public class ServicioEsAfectado{
    private Servicio servicio;
    private Boolean esAfectado;

    public ServicioEsAfectado(Servicio servicio, Membresia membresia){
      this.servicio = servicio;
      if(membresia != null) {
        this.esAfectado = membresia.estaAfectado(servicio);
      }else{
        this.esAfectado = true;
      }
    }
  }
}
