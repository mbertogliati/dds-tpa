package ar.edu.utn.frba.dds.controllers.generales.comunidades;

import ar.edu.utn.frba.dds.controllers.exceptions.FormInvalidoException;
import ar.edu.utn.frba.dds.controllers.utils.VerificadorRol;
import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.controllers.utils.builders.builderPersona.PersonaBuilder;
import ar.edu.utn.frba.dds.controllers.utils.builders.builderPersona.PersonaBuilderHashmap;
import ar.edu.utn.frba.dds.controllers.utils.builders.builderUsuario.UsuarioBuilder;
import ar.edu.utn.frba.dds.controllers.utils.builders.builderUsuario.UsuarioBuilderHashmap;
import ar.edu.utn.frba.dds.controllers.utils.factories.ValidadorUsuarioFactory.ValidadorConcretoFactory;
import ar.edu.utn.frba.dds.controllers.utils.factories.ValidadorUsuarioFactory.ValidadorUsuarioFactory;
import ar.edu.utn.frba.dds.modelos.comunidades.Interes;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.hasheo.EstrategiaHash;
import ar.edu.utn.frba.dds.modelos.hasheo.HashPBKDF2;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.modelos.utilidades.FechasDeSemana;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import ar.edu.utn.frba.dds.repositorios.converters.EstrategiaMomentoNotificacionConverter;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.DepartamentoRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.LocalidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.ProvinciaRepositorio;
import ar.edu.utn.frba.dds.repositorios.servicios.ServicioPrestadoRepositorio;
import ar.edu.utn.frba.dds.repositorios.utilidades.FechasDeSemanaRepositorio;
import io.javalin.http.Context;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class UsuariosController implements ICrudViewsHandler {
  private static final ValidadorUsuarioFactory factoryValidador = new ValidadorConcretoFactory();
  private static final EstrategiaHash hasheador = new HashPBKDF2();
  private static final EstrategiaMomentoNotificacionConverter converterMomentoNotificacion = new EstrategiaMomentoNotificacionConverter();
  private UsuarioBuilder usuarioBuilder;
  private PersonaBuilder personaBuilder;

  private PersonaRepositorio repoPersona;
  private UsuarioRepositorio repoUsuario;
  private FechasDeSemanaRepositorio repoFechas;
  private ProvinciaRepositorio repoProvincia;
  private DepartamentoRepositorio repoDepartamento;
  private LocalidadRepositorio repoLocalidad;
  private ServicioPrestadoRepositorio repoServicioPrestado;
  private EntidadRepositorio repoEntidad;


  public UsuariosController(EntityManager entityManager){
    repoPersona = new PersonaRepositorio(entityManager);
    repoFechas = new FechasDeSemanaRepositorio(entityManager);
    repoUsuario = new UsuarioRepositorio(entityManager);
    repoProvincia = new ProvinciaRepositorio(entityManager);
    repoDepartamento = new DepartamentoRepositorio(entityManager);
    repoLocalidad = new LocalidadRepositorio(entityManager);
    this.repoServicioPrestado = new ServicioPrestadoRepositorio(entityManager);
    this.repoEntidad = new EntidadRepositorio(entityManager);
  }

  public void agregarServicio(Context context){
    Usuario usuario = context.sessionAttribute("usuario");
    Interes interes = usuario.getPersonaAsociada().getInteres();

    ServicioPrestado servicioPrestado = repoServicioPrestado.buscarPorId(Integer.parseInt(context.formParam("servicio")));

    interes.agregarServicio(servicioPrestado);
    repoPersona.actualizar(usuario.getPersonaAsociada());

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Servicio agregado correctamente."));
    context.redirect("/usuarios/"+usuario.getId()+"/interes?success");
  }

  public void agregarEntidad(Context context){
    Usuario usuario = context.sessionAttribute("usuario");
    Interes interes = usuario.getPersonaAsociada().getInteres();

    Entidad entidad = repoEntidad.buscarPorId(Integer.parseInt(context.formParam("entidad")));

    interes.agregarEntidad(entidad);
    repoPersona.actualizar(usuario.getPersonaAsociada());

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Entidad agregada correctamente."));
    context.redirect("/usuarios/"+usuario.getId()+"/interes?success");
  }

  public void sacarEntidad(Context context){
    Usuario usuario = context.sessionAttribute("usuario");
    Interes interes = usuario.getPersonaAsociada().getInteres();

    Entidad entidad = repoEntidad.buscarPorId(Integer.parseInt(context.pathParam("idEntidad")));

    interes.eliminarEntidad(entidad);
    repoPersona.actualizar(usuario.getPersonaAsociada());

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Entidad eliminada correctamente."));
    context.redirect("/usuarios/"+usuario.getId()+"/interes?success");
  }

  public void sacarServicio(Context context){
    Usuario usuario = context.sessionAttribute("usuario");
    Interes interes = usuario.getPersonaAsociada().getInteres();

    ServicioPrestado servicioPrestado = repoServicioPrestado.buscarPorId(Integer.parseInt(context.pathParam("idServicio")));

    interes.eliminarServicio(servicioPrestado);
    repoPersona.actualizar(usuario.getPersonaAsociada());

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Servicio eliminado correctamente."));
    context.redirect("/usuarios/"+usuario.getId()+"/interes?success");
  }

  public void getInteres(Context context){
    Map<String, Object> model = GeneradorModel.model(context);

    Usuario usuario = context.sessionAttribute("usuario");

    Interes interes = usuario.getPersonaAsociada().getInteres();

    model.put("interes", interes);

    model.put("serviciosGenerales", repoServicioPrestado.buscarTodos());
    model.put("entidades", repoEntidad.buscarTodas());

    context.render("verInteres.hbs", model);
  }

  @Override
  public void index(@NotNull Context context){
    Usuario usuario = context.sessionAttribute("usuario");
    if (!VerificadorRol.tieneRol(usuario, VerificadorRol.Permiso.ADMINISTRAR_USUARIOS)){
      context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.ERROR, "Error. No tenés permisos suficientes para ver esa página."));
      context.redirect("/");
      return;
    }

    Map<String, Object> model = GeneradorModel.model(context);
    model.put("adminPlataforma", true);

    String param = context.queryParam("username");
    List<Usuario> listaUsuarios;

    if(param != null){
      listaUsuarios = repoUsuario.buscarPorUsername(param);
    }else{
      listaUsuarios = repoUsuario.buscarTodos();
    }

    model.put("usuarios", listaUsuarios);

    context.render("usuarios.hbs", model);
  }
  public void show(@NotNull Context context){
    Usuario usuario = context.sessionAttribute("usuario");
    if (!VerificadorRol.tieneRol(usuario, VerificadorRol.Permiso.ADMINISTRAR_USUARIOS)){
      context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.ERROR, "Error. No tenés permisos suficientes para ver esa página."));
      context.redirect("/");
      return;
    }

    Map<String,Object> model = GeneradorModel.model(context);

    String id = context.pathParam("id");
    model.put("adminPlataforma", true);
    model.put("persona", repoPersona.buscarPorId(Integer.parseInt(id)));
    context.render("administrarUsuario.hbs",model);
  }

  @Override
  public void create(Context context) {

  }

  @Override
  public void save(Context context) {

  }

  public void edit(@NotNull Context context){
    Usuario usuario = repoUsuario.buscarPorId(Integer.parseInt(context.pathParam("id")));
    if(context.sessionAttribute("adminPlataforma") == null && !usuario.equals(context.sessionAttribute("usuario"))){
      context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.ERROR, "Error. No tenés permisos suficientes para ver esa página."));
      context.redirect("/");
    }

    Map<String,Object> model = GeneradorModel.model(context);

    Persona persona = usuario.getPersonaAsociada();

    String momentoNotificacion = converterMomentoNotificacion.convertToDatabaseColumn(usuario.getPersonaAsociada().getEstrategiaMomentoNotificacion());
    model.put("usuario",usuario);
    model.put("medioNotificacion",persona.getMetodoNotificacion());
    model.put("momentoNotificacion",momentoNotificacion);
    model.put("fechasDeSemana",persona.getFechas());

    model.put("provincias", repoProvincia.buscarTodas());
    model.put("departamentos", repoDepartamento.buscarTodos());
    model.put("localidades", repoLocalidad.obtenerTodas());

    context.render("editarUsuario.hbs", model);
  }

  public void update(@NotNull Context context) {
    Usuario usuario = repoUsuario.buscarPorId(Integer.parseInt(context.pathParam("id")));
    usuarioBuilder = new UsuarioBuilderHashmap(context.formParamMap(),null).init(usuario);
    personaBuilder = new PersonaBuilderHashmap(context.formParamMap(),repoLocalidad);

    if (context.sessionAttribute("adminPlataforma") == null && !usuario.equals(context.sessionAttribute("usuario"))) {
      context.redirect("/");
    }

    if (!usuario.getPassword().equals(hasheador.hashear(context.formParam("vieja_password")))) {
      throw new FormInvalidoException("La contraseña actual no coincide con la ingresada");
    }

    String password = context.formParam("password");
    String passwordRepetida = context.formParam("repetir_password");

    if(!password.equals("")){
      if(!Objects.equals(password,passwordRepetida)){
        throw new FormInvalidoException("Las contraseñas no coinciden");
      }
      if(!factoryValidador.crearValidador().validar(usuario,context.formParam("password"))){
        throw new FormInvalidoException("La contraseña no cumple con los requisitos mínimos");
      }
      usuarioBuilder.configurarPassword();
    }
    usuarioBuilder.configurarUsuario();
    usuario = usuarioBuilder.get();

    Persona persona = personaBuilder
            .init(usuario.getPersonaAsociada())
            .configurarNombres()
            .configurarInformacionDeContacto()
            .configurarPreferenciasNotificacion()
            .configurarInformacionDeUbicacion()
            .get();

    //TODO: No elimina fechas viejas
    //ELIMINO FECHAS VIEJAS
    List<FechasDeSemana> fechasViejas = persona.getFechas();
    if (fechasViejas != null) fechasViejas.forEach(f -> repoFechas.eliminar(f));

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Usuario modificado correctamente."));
    context.redirect("/usuarios/"+usuario.getId()+"/edit?success");
  }

  @Override
  public void delete(Context context) {
    Usuario usuario = repoUsuario.buscarPorId(Integer.parseInt(context.pathParam("id")));
    usuario.setActivo(false);
    repoUsuario.actualizar(usuario);
    context.sessionAttribute("msg",new MensajeVista(MensajeVista.TipoMensaje.SUCCESS,"Usuario eliminado correctamente"));
    context.redirect("/");
  }
}
