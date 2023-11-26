package ar.edu.utn.frba.dds.controllers.generales.comunidades;

import static ar.edu.utn.frba.dds.controllers.utils.TipoPermiso.ADMINISTRAR_COMUNIDAD;

import ar.edu.utn.frba.dds.controllers.utils.VerificadorRol;
import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Membresia;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.comunidades.ComunidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.MembresiaRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.RolRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioPrestadoRepositorio;
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
  private ServicioPrestadoRepositorio repoServicioPrestado;
  private MembresiaRepositorio repoMembresia;
  private RolRepositorio repoRol;


  public ComunidadesController(EntityManager entityManager){
    this.repoComunidad = new ComunidadRepositorio(entityManager);
    this.repoUsuario = new UsuarioRepositorio(entityManager);
    this.repoServicio = new ServicioRepositorio(entityManager);
    this.repoMembresia = new MembresiaRepositorio(entityManager);
    this.repoRol = new RolRepositorio(entityManager);
    this.repoServicioPrestado = new ServicioPrestadoRepositorio(entityManager);
  }

  public void deUsuario(Context context){
    Map<String, Object> model = GeneradorModel.model(context);

    Usuario usuario = context.sessionAttribute("usuario");

    List<Comunidad> comunidadesDeUsuario = repoComunidad.obtenerTodas().stream().filter(c -> c.getMembresias().stream().anyMatch(m -> m.getPersona().getId() == usuario.getPersonaAsociada().getId())).toList();

    model.put("comunidades", obtenerComunidadesConUsuarioActual(comunidadesDeUsuario, usuario));

    context.render("comunidades.hbs", model);
  }

  public void cambiarRol(Context context){
    Map<String, Object> model = GeneradorModel.model(context);

    Usuario usuario = repoUsuario.buscarPorId(Integer.parseInt(context.pathParam("idUsuario")));
    ServicioPrestado servicioPrestado = repoServicioPrestado.buscarPorId(Integer.parseInt(context.pathParam("idServicio")));
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("idComunidad")));

    if(context.pathParam("nuevoRol").equals("afectado")){
      comunidad.getMembresia(usuario.getPersonaAsociada()).agregarServicioAfectado(servicioPrestado);
    }else{
      comunidad.getMembresia(usuario.getPersonaAsociada()).agregarServicioObservado(servicioPrestado);
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
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("idComunidad")));

    ComunidadConUsuarioActual comunidadConUsuarioActual = new ComunidadConUsuarioActual(comunidad,usuario.getPersonaAsociada());
    model.put("comunidadSeleccionada", comunidadConUsuarioActual);

    context.render("verComunidad.hbs", model);
  }

  @Override
  public void create(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    Comunidad comunidadVacia = new Comunidad();
    model.put("comunidad", comunidadVacia);
    model.put("editable", true);
    model.put("creacion", true);
    model.put("serviciosGenerales", repoServicioPrestado.buscarTodos());

    context.render("verComunidad.hbs", model);
  }

  @Override
  public void save(Context context) {
    Comunidad comunidad = new Comunidad();

    comunidad.setNombre(context.formParam("nombre"));
    comunidad.setDetalle(context.formParam("detalle"));

    List<String> idServicios = context.formParams("servicios[]");
    List<ServicioPrestado> servicios = new ArrayList<>();
    for (String id : idServicios){
      servicios.add(repoServicioPrestado.buscarPorId(Integer.parseInt(id)));
    }
    comunidad.setServiciosPrestados(servicios);

    repoComunidad.guardarComunidad(comunidad);

    Usuario usuario = context.sessionAttribute("usuario");
    Membresia membresia = new Membresia(comunidad, usuario.getPersonaAsociada(), repoRol.rolAdminComunidad());
    repoMembresia.guardar(membresia);

    Membresia membresia1 = new Membresia(comunidad, usuario.getPersonaAsociada(), repoRol.rolDefaultComunidad());
    repoMembresia.guardar(membresia1);

    comunidad.agregarMembresia(membresia);
    comunidad.agregarMembresia(membresia1);

    repoUsuario.actualizar(usuario);
    repoComunidad.actualizarComunidad(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Comunidad creada correctamente."));
    context.redirect("/comunidades/"+comunidad.getId()+"/edit?success");
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    Usuario usuario = context.sessionAttribute("usuario");
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("idComunidad")));

    if(VerificadorRol.tienePermiso(usuario, comunidad, ADMINISTRAR_COMUNIDAD)){
      model.put("editable", true);
      model.put("edicion", true);
      model.put("adminComunidad", true);
    }

    model.put("comunidadSeleccionada", new ComunidadConUsuarioActual(comunidad,usuario.getPersonaAsociada()));
    model.put("serviciosGenerales", repoServicioPrestado.buscarTodos());

    context.render("verComunidad.hbs", model);
  }

  @Override
  public void update(Context context) {
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("idComunidad")));

    comunidad.setNombre(context.formParam("nombre"));
    comunidad.setDetalle(context.formParam("detalle"));

    repoComunidad.actualizarComunidad(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Comunidad modificada correctamente."));
    context.redirect("/comunidades/"+comunidad.getId()+"/edit?success");
  }

  @Override
  public void delete(Context context) {
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("idComunidad")));

    repoComunidad.eliminarComunidad(comunidad);

    context.sessionAttribute("msg",new MensajeVista(MensajeVista.TipoMensaje.SUCCESS,"Comunidad eliminada correctamente"));
    context.redirect("/comunidades");
  }

  public void sacarMiembro(Context context){
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("idComunidad")));

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
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("idComunidad")));

    Usuario usuario = repoUsuario.buscarPorId(Integer.parseInt(context.pathParam("idUsuario")));

    Membresia membresia = new Membresia(comunidad, usuario.getPersonaAsociada(), repoRol.rolDefaultComunidad());
    repoMembresia.guardar(membresia);
    //comunidad.agregarPersona(usuario.getPersonaAsociada(), repoRol.rolDefaultComunidad());

    comunidad.agregarMembresia(membresia);

    repoUsuario.actualizar(usuario);
    repoComunidad.actualizarComunidad(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Membresia agregada correctamente."));
    context.redirect("/comunidades/?success");
  }

  public void agregarServicio(Context context){
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("idComunidad")));

    ServicioPrestado servicioPrestado = repoServicioPrestado.buscarPorId(Integer.parseInt(context.formParam("servicio")));

    comunidad.agregarServicio(servicioPrestado);

    repoComunidad.actualizarComunidad(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Servicio agregado correctamente."));
    context.redirect("/comunidades/"+comunidad.getId()+"/edit?success");
  }

  public void quitarServicio(Context context){
    Comunidad comunidad = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.pathParam("idComunidad")));

    ServicioPrestado servicioPrestado = repoServicioPrestado.buscarPorId(Integer.parseInt(context.pathParam("idServicio")));

    comunidad.eliminarServicio(servicioPrestado);

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
    private List<ServicioEsAfectado> serviciosPrestados;
    private Boolean tieneAUsuarioActual;

    public ComunidadConUsuarioActual(Comunidad comunidad, Persona persona){
      this.nombre = comunidad.getNombre();
      this.detalle = comunidad.getDetalle();
      this.serviciosPrestados = new ArrayList<>();
      for (ServicioPrestado servicio : comunidad.getServiciosPrestados()){
        this.serviciosPrestados.add(new ServicioEsAfectado(servicio, comunidad.getMembresia(persona)));
      }
      this.id = comunidad.getId();
      this.tieneAUsuarioActual = comunidad.getMembresias().stream().anyMatch(m -> m.getPersona().getId() == persona.getId());
    }
  }

  @Getter
  @Setter
  public class ServicioEsAfectado{
    private ServicioPrestado servicioPrestado;
    private Boolean esAfectado;

    public ServicioEsAfectado(ServicioPrestado servicioPrestado, Membresia membresia){
      this.servicioPrestado = servicioPrestado;
      if(membresia != null) {
        this.esAfectado = membresia.estaAfectado(servicioPrestado);
      }else{
        this.esAfectado = true;
      }
    }
  }
}
