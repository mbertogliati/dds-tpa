package ar.edu.utn.frba.dds.controllers.generales.comunidades;

import static ar.edu.utn.frba.dds.controllers.utils.TipoPermiso.ADMINISTRAR_COMUNIDAD;

import ar.edu.utn.frba.dds.controllers.utils.VerificadorRol;
import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
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
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

public class ComunidadesController implements ICrudViewsHandler {
  private ComunidadRepositorio repoComunidad;
  private UsuarioRepositorio repoUsuario;
  private ServicioRepositorio repoServicio;
  private ServicioPrestadoRepositorio repoServicioPrestado;
  private MembresiaRepositorio repoMembresia;
  private RolRepositorio repoRol;


  public ComunidadesController(){
    this.repoComunidad = new ComunidadRepositorio();
    this.repoUsuario = new UsuarioRepositorio();
    this.repoServicio = new ServicioRepositorio();
    this.repoMembresia = new MembresiaRepositorio();
    this.repoRol = new RolRepositorio();
    this.repoServicioPrestado = new ServicioPrestadoRepositorio();
  }

  public void deUsuario(Context context){
    Map<String, Object> model = GeneradorModel.model(context);

    Usuario usuario = context.sessionAttribute("usuario");

    List<Comunidad> comunidadesDeUsuario = repoComunidad.obtenerTodas().stream().filter(c -> c.getMembresias().stream().anyMatch(m -> m.getPersona().getId() == usuario.getPersonaAsociada().getId())).toList();

    model.put("comunidades", obteneresConUsuarioActual(comunidadesDeUsuario, usuario));

    context.render("comunidades.hbs", model);
  }

  public void cambiarRol(Context context){
    Map<String, Object> model = GeneradorModel.model(context);

    Usuario usuario = repoUsuario.buscarPorId(Integer.parseInt(context.pathParam("idUsuario")));
    ServicioPrestado servicioPrestado = repoServicioPrestado.buscarPorId(Integer.parseInt(context.pathParam("idServicio")));
    Comunidad comunidad = repoComunidad.obtenerPorId(Integer.parseInt(context.pathParam("idComunidad")));

    if(context.pathParam("nuevoRol").equals("afectado")){
      comunidad.getMembresia(usuario.getPersonaAsociada()).agregarServicioAfectado(servicioPrestado);
    }else{
      comunidad.getMembresia(usuario.getPersonaAsociada()).agregarServicioObservado(servicioPrestado);
    }

    repoComunidad.actualizar(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Rol cambiado correctamente."));
    context.redirect("/comunidades/"+comunidad.getId()+"?success");
  }


  @Override
  public void index(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    model.put("comunidades", obteneresConUsuarioActual(repoComunidad.obtenerTodas(), context.sessionAttribute("usuario")));

    context.render("comunidades.hbs", model);
  }

  @Override
  public void show(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    Usuario usuario = context.sessionAttribute("usuario");
    Comunidad comunidad = repoComunidad.obtenerPorId(Integer.parseInt(context.pathParam("idComunidad")));
    repoComunidad.refresh(comunidad);

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

    repoComunidad.guardar(comunidad);

    Usuario usuario = context.sessionAttribute("usuario");

    Membresia membresia = new Membresia(comunidad, usuario.getPersonaAsociada(), repoRol.rolAdminComunidad());
    membresia.getRoles().add(this.repoRol.rolDefaultComunidad());

    repoMembresia.guardar(membresia);
    comunidad.agregarMembresia(membresia);

    repoUsuario.actualizar(usuario);
    repoComunidad.actualizar(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Comunidad creada correctamente."));
    context.redirect("/comunidades/"+comunidad.getId()+"/edit?success");
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    Usuario usuario = context.sessionAttribute("usuario");
    Comunidad comunidad = repoComunidad.obtenerPorId(Integer.parseInt(context.pathParam("idComunidad")));
    repoComunidad.refresh(comunidad);

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
    Comunidad comunidad = repoComunidad.obtenerPorId(Integer.parseInt(context.pathParam("idComunidad")));

    comunidad.setNombre(context.formParam("nombre"));
    comunidad.setDetalle(context.formParam("detalle"));

    repoComunidad.actualizar(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Comunidad modificada correctamente."));
    context.redirect("/comunidades/"+comunidad.getId()+"/edit?success");
  }

  @Override
  public void delete(Context context) {
    Comunidad comunidad = repoComunidad.obtenerPorId(Integer.parseInt(context.pathParam("idComunidad")));

    repoComunidad.actualizar(comunidad);

    context.sessionAttribute("msg",new MensajeVista(MensajeVista.TipoMensaje.SUCCESS,"Comunidad eliminada correctamente"));
    context.redirect("/comunidades");
  }

  public void sacarMiembro(Context context){
    Comunidad comunidad = repoComunidad.obtenerPorId(Integer.parseInt(context.pathParam("idComunidad")));

    Usuario usuario = repoUsuario.buscarPorId(Integer.parseInt(context.pathParam("idUsuario")));

     List<Membresia> membresias = comunidad.getMembresias().stream().filter(m -> m.getPersona().getId() == usuario.getPersonaAsociada().getId()).toList();

     membresias.forEach(comunidad::eliminarMembresia);
     membresias.forEach(usuario.getPersonaAsociada()::eliminarMembresia);
     membresias.forEach(membresia -> repoMembresia.eliminar(membresia));

    repoComunidad.actualizar(comunidad);
    repoUsuario.actualizar(usuario);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Membresia eliminada correctamente."));
    context.redirect("/comunidades?success");
  }

  public void unirMiembro(Context context){
    Comunidad comunidad = repoComunidad.obtenerPorId(Integer.parseInt(context.pathParam("idComunidad")));

    Usuario usuario = repoUsuario.buscarPorId(Integer.parseInt(context.pathParam("idUsuario")));

    Membresia membresia = new Membresia(comunidad, usuario.getPersonaAsociada(), repoRol.rolDefaultComunidad());
    repoMembresia.guardar(membresia);
    //comunidad.agregarPersona(usuario.getPersonaAsociada(), repoRol.rolDefaultComunidad());

    comunidad.agregarMembresia(membresia);

    repoUsuario.actualizar(usuario);
    repoComunidad.actualizar(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Membresia agregada correctamente."));
    context.redirect("/comunidades/?success");
  }

  public void agregarServicio(Context context){
    Comunidad comunidad = repoComunidad.obtenerPorId(Integer.parseInt(context.pathParam("idComunidad")));

    ServicioPrestado servicioPrestado = repoServicioPrestado.buscarPorId(Integer.parseInt(context.formParam("servicio")));

    comunidad.agregarServicio(servicioPrestado);
    repoComunidad.actualizar(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Servicio agregado correctamente."));
    context.redirect("/comunidades/"+comunidad.getId()+"/edit?success");
  }

  public void quitarServicio(Context context){
    Comunidad comunidad = repoComunidad.obtenerPorId(Integer.parseInt(context.pathParam("idComunidad")));

    ServicioPrestado servicioPrestado = repoServicioPrestado.buscarPorId(Integer.parseInt(context.pathParam("idServicio")));

    comunidad.eliminarServicio(servicioPrestado);
    repoComunidad.actualizar(comunidad);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Servicio eliminado correctamente."));
    context.redirect("/comunidades/"+comunidad.getId()+"/edit?success");
  }
  private List<ComunidadConUsuarioActual> obteneresConUsuarioActual(List<Comunidad> comunidades, Usuario usuario){
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
