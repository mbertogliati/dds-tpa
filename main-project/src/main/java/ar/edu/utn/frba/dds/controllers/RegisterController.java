package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.hasheo.EstrategiaHash;
import ar.edu.utn.frba.dds.modelos.hasheo.HashPBKDF2;
import ar.edu.utn.frba.dds.modelos.validacion.EstrategiaValidacionNoEstaEnLista;
import ar.edu.utn.frba.dds.modelos.validacion.EstrategiaValidacionRegExp;
import ar.edu.utn.frba.dds.modelos.validacion.ObtenerTopPeoresPasswords;
import ar.edu.utn.frba.dds.modelos.validacion.ValidadorUsuario;
import ar.edu.utn.frba.dds.modelos.validacion.ValidadorUsuarioConcreto;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.RolRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class RegisterController{

  private static ValidadorUsuario validador;
  private static EstrategiaHash hasheador = new HashPBKDF2();

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
  private RolRepositorio repoRol;

  public RegisterController(EntityManager entityManager){
    this.repoPersona = new PersonaRepositorio(entityManager);
    this.repoUsuario = new UsuarioRepositorio(entityManager);
    this.repoRol = new RolRepositorio(entityManager);
  }

  public void create(@NotNull Context context) throws Exception {
    String param = context.queryParam("error");

    if(param != null){
      Map<String, Object> model = GeneradorModel.model(context);
      model.put("error", "Revise sus datos");
      context.render("register.hbs", model);
    }else{
      context.render("register.hbs");
    }

  }
  public void save(@NotNull Context context) throws Exception {

    Usuario usuario = new Usuario();
    usuario.setUsername(context.formParam("username"));


    if(!Objects.equals(context.formParam("password"), context.formParam("repetir_password"))
        || !validador.validar(usuario,context.formParam("password"))){
      context.redirect("/register?error=register");
      return;
    }

    usuario.setPassword(hasheador.hashear(context.formParam("password")));
    usuario.setRolPlataforma(repoRol.rolDefault());

    Persona nuevaPersona = new Persona(context.formParam("nombre"),context.formParam("apellido"));
    usuario.setPersonaAsociada(nuevaPersona);
    nuevaPersona.setEmail(context.formParam("email"));
    nuevaPersona.setWhatsapp(Integer.parseInt(context.formParam("celular")));

    repoPersona.guardar(nuevaPersona);
    repoUsuario.guardar(usuario);

    context.sessionAttribute("usuario", usuario);
    context.redirect("/");
  }
}
