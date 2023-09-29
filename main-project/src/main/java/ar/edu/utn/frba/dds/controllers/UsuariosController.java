package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Interes;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.modelos.hasheo.EstrategiaHash;
import ar.edu.utn.frba.dds.modelos.hasheo.HashPBKDF2;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.MetadatoGeografico;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.modelos.utilidades.FechasDeSemana;
import ar.edu.utn.frba.dds.modelos.validacion.EstrategiaValidacionNoEstaEnLista;
import ar.edu.utn.frba.dds.modelos.validacion.EstrategiaValidacionRegExp;
import ar.edu.utn.frba.dds.modelos.validacion.ObtenerTopPeoresPasswords;
import ar.edu.utn.frba.dds.modelos.validacion.ValidadorUsuario;
import ar.edu.utn.frba.dds.modelos.validacion.ValidadorUsuarioConcreto;
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
import io.javalin.http.Handler;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public class UsuariosController implements ICrudViewsHandler {
  //TODO: Crear Factory de personas/usuarios
  private static ValidadorUsuario validador;
  private static EstrategiaHash hasheador = new HashPBKDF2();
  private static EstrategiaMomentoNotificacionConverter converterMomentoNotificacion = new EstrategiaMomentoNotificacionConverter();


  static {
    ValidadorUsuarioConcreto validadorConcreto = new ValidadorUsuarioConcreto();
    validadorConcreto.agregarEstrategias(
        new EstrategiaValidacionRegExp("[A-Z]"),
        new EstrategiaValidacionRegExp("[a-z]"),
        new EstrategiaValidacionRegExp("[0-9]"),
        new EstrategiaValidacionRegExp("[#?!@$ %^&*-]"),
        new EstrategiaValidacionRegExp(".{8,}"),
        new EstrategiaValidacionNoEstaEnLista(ObtenerTopPeoresPasswords.instancia())
    );
    validador = validadorConcreto;
  }
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

    context.redirect("/usuarios/"+usuario.getId()+"/interes?result=successAddServicio");
  }

  public void agregarEntidad(Context context){
    Usuario usuario = context.sessionAttribute("usuario");
    Interes interes = usuario.getPersonaAsociada().getInteres();

    Entidad entidad = repoEntidad.buscarPorId(Integer.parseInt(context.formParam("entidad")));

    interes.agregarEntidad(entidad);
    repoPersona.actualizar(usuario.getPersonaAsociada());

    context.redirect("/usuarios/"+usuario.getId()+"/interes?result=successAddEntidad");
  }

  public void sacarEntidad(Context context){
    Usuario usuario = context.sessionAttribute("usuario");
    Interes interes = usuario.getPersonaAsociada().getInteres();

    Entidad entidad = repoEntidad.buscarPorId(Integer.parseInt(context.pathParam("idEntidad")));

    interes.eliminarEntidad(entidad);
    repoPersona.actualizar(usuario.getPersonaAsociada());

    context.redirect("/usuarios/"+usuario.getId()+"/interes?result=successSacarEntidad");
  }

  public void sacarServicio(Context context){
    Usuario usuario = context.sessionAttribute("usuario");
    Interes interes = usuario.getPersonaAsociada().getInteres();

    ServicioPrestado servicioPrestado = repoServicioPrestado.buscarPorId(Integer.parseInt(context.pathParam("idServicio")));

    interes.eliminarServicio(servicioPrestado);
    repoPersona.actualizar(usuario.getPersonaAsociada());

    context.redirect("/usuarios/"+usuario.getId()+"/interes?result=successSacarServicio");
  }

  public void getInteres(Context context){
    Map<String, Object> model = GeneradorModel.model(context);

    Usuario usuario = context.sessionAttribute("usuario");

    Interes interes = usuario.getPersonaAsociada().getInteres();

    model.put("interes", interes);

    String result = context.queryParam("result");
    if(result != null){
      switch(result){
        case "successAddServicio":
          model.put("msg",  new MensajeVista("success", "Servicio agregado correctamente"));
          break;
        case "successSacarServicio":
          model.put("msg", new MensajeVista("success", "Servicio eliminado correctamente"));
          break;
        case "successSacarEntidad":
          model.put("msg",  new MensajeVista("success", "Entidad eliminada correctamente"));
          break;
        case "successAddEntidad":
          model.put("msg",  new MensajeVista("success", "Entidad agregada correctamente"));
          break;
      }
    }

    model.put("serviciosGenerales", repoServicioPrestado.buscarTodos());
    model.put("entidades", repoEntidad.buscarTodos());

    context.render("verInteres.hbs", model);
  }

  @Override
  public void index(@NotNull Context context){

    Usuario usuario = context.sessionAttribute("usuario");
    if (!VerificadorRol.tieneRol(usuario, VerificadorRol.Permiso.ADMINISTRAR_USUARIOS)){
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
      context.redirect("/");
    }

    Map<String,Object> model = GeneradorModel.model(context);

    String result = context.queryParam("result");
    if(result != null){
      if(result.equals("success")){
        model.put("success", true);
      }else{
        model.put("error", true);
      }
    }

    Persona persona = usuario.getPersonaAsociada();

    String momentoNotificacion = converterMomentoNotificacion.convertToDatabaseColumn(usuario.getPersonaAsociada().getEstrategiaMomentoNotificacion());
    model.put("usuario",usuario);
    model.put("medioNotificacion",persona.getMetodoNotificacion());
    model.put("momentoNotificacion",momentoNotificacion);
    model.put("fechasDeSemana",persona.getFechas());

    //BUSCO PROVINCIAS
    StringBuilder stringBuilder = new StringBuilder("");
    List<Provincia> provincias = repoProvincia.buscarTodas();
    for (Provincia provincia : provincias){
      if(provincia.getId().equals(persona.getUltimaUbicacion().getMetadato().getProvincia().getId())){
        stringBuilder.append("<option value=\"" + provincia.getId() + "\" selected>" + provincia.getNombre() + "</option>");
      }else{
        stringBuilder.append("<option value=\"" + provincia.getId() + "\">" + provincia.getNombre() + "</option>");
      }
    }
    model.put("provincias", stringBuilder.toString());

    //BUSCO DEPARTAMENTOS
    stringBuilder = new StringBuilder("");
    List<Departamento> departamentos = repoDepartamento.buscarPorProvincia(persona.getUltimaUbicacion().getMetadato().getProvincia().getId());
    for (Departamento departamento : departamentos){
      if(departamento.getId().equals(persona.getUltimaUbicacion().getMetadato().getDepartamento().getId())){
        stringBuilder.append("<option value=\"" + departamento.getId() + "\" selected>" + departamento.getNombre() + "</option>");
      }else{
        stringBuilder.append("<option value=\"" + departamento.getId() + "\">" + departamento.getNombre() + "</option>");
      }
    }
    model.put("departamentos", stringBuilder.toString());

    //BUSCO LOCALIDADES
    stringBuilder = new StringBuilder("");
    List<Localidad> localidades = repoLocalidad.buscarPorDepartamento(persona.getUltimaUbicacion().getMetadato().getDepartamento().getId());
    for (Localidad localidad : localidades){
      if(localidad.getId().equals(persona.getUltimaUbicacion().getMetadato().getLocalidad().getId())){
        stringBuilder.append("<option value=\"" + localidad.getId() + "\" selected>" + localidad.getNombre() + "</option>");
      }else{
        stringBuilder.append("<option value=\"" + localidad.getId() + "\">" + localidad.getNombre() + "</option>");
      }
    }
    model.put("localidades", stringBuilder.toString());

    context.render("editarUsuario.hbs", model);
  }
  public void update(@NotNull Context context) {
    Usuario usuario = repoUsuario.buscarPorId(Integer.parseInt(context.pathParam("id")));

    if (context.sessionAttribute("adminPlataforma") == null && !usuario.equals(context.sessionAttribute("usuario"))) {
      context.redirect("/");
    }

    if (!usuario.getPassword().equals(hasheador.hashear(context.formParam("vieja_password")))) {
      context.redirect(context.path() + "?result=error");
    }

    Persona persona = usuario.getPersonaAsociada();
    String password = context.formParam("password");
    String passwordRepetida = context.formParam("repetir_password");

    if(password != ""){
      if(!Objects.equals(password,passwordRepetida)
          || !validador.validar(usuario,password)){
        context.redirect(context.path()+"?result=error");
        return;
      }
      usuario.setPassword(hasheador.hashear(password));
    }

    usuario.setUsername(context.formParam("username"));

    //TODO: NO ELIMINA FECHAS VIEJAS
    //ELIMINO FECHAS VIEJAS
    List<FechasDeSemana> fechasViejas = persona.getFechas();
    fechasViejas.forEach(f -> repoFechas.eliminar(f));

    //CARGO FECHAS NUEVAS
    List<String> dias = context.formParams("dias[]");
    List<String> horas = context.formParams("horas[]");
    List<FechasDeSemana> fechasSemana = new ArrayList<>();
    for(int i = 0; i< dias.size(); i++){
      fechasSemana.add(new FechasDeSemana(DayOfWeek.valueOf(dias.get(i)), LocalTime.parse(horas.get(i))));
    }

    Localidad localidad = repoLocalidad.obtenerLocalidadPorId(context.formParam("localidad"));
    MetadatoGeografico geografico = new MetadatoGeografico(localidad);
    persona.getUltimaUbicacion().setMetadato(geografico);

    persona.setFechas(fechasSemana);

    persona.setNombre(context.formParam("nombre"));
    persona.setApellido(context.formParam("apellido"));
    persona.setEmail(context.formParam("email"));
    persona.setWhatsapp(Integer.parseInt(context.formParam("celular")));
    persona.setMetodoNotificacion(context.formParam("medio_notificacion"));
    persona.setEstrategiaMomentoNotificacion(converterMomentoNotificacion.convertToEntityAttribute(context.formParam("momento_notificacion")));
    repoUsuario.actualizar(usuario);

    context.redirect("/usuarios/"+usuario.getId()+"/edit?result=success");
  }

  @Override
  public void delete(Context context) {

  }
}
