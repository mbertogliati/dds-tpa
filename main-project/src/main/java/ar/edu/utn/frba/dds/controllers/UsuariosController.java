package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.modelos.hasheo.EstrategiaHash;
import ar.edu.utn.frba.dds.modelos.hasheo.HashPBKDF2;
import ar.edu.utn.frba.dds.modelos.utilidades.FechasDeSemana;
import ar.edu.utn.frba.dds.modelos.validacion.EstrategiaValidacionNoEstaEnLista;
import ar.edu.utn.frba.dds.modelos.validacion.EstrategiaValidacionRegExp;
import ar.edu.utn.frba.dds.modelos.validacion.ObtenerTopPeoresPasswords;
import ar.edu.utn.frba.dds.modelos.validacion.ValidadorUsuario;
import ar.edu.utn.frba.dds.modelos.validacion.ValidadorUsuarioConcreto;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import ar.edu.utn.frba.dds.repositorios.converters.EstrategiaMomentoNotificacionConverter;
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


  public UsuariosController(EntityManager entityManager){
    repoPersona = new PersonaRepositorio(entityManager);
    repoFechas = new FechasDeSemanaRepositorio(entityManager);
    repoUsuario = new UsuarioRepositorio(entityManager);
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
