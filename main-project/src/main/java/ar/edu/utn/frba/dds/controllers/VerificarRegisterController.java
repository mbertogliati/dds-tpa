package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.CreadorEntityManager;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Rol;
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
import java.util.Objects;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class VerificarRegisterController implements Handler {
  private static ValidadorUsuario validador;
  private static EstrategiaHash hasheador = new HashPBKDF2();
  private static PersonaRepositorio repoPersona;
  private static UsuarioRepositorio repoUsuario;
  private static RolRepositorio repoRol;

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

  public VerificarRegisterController(EntityManager entityManager){
    repoPersona = new PersonaRepositorio(entityManager);
    repoUsuario = new UsuarioRepositorio(entityManager);
    repoRol = new RolRepositorio(entityManager);
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
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
    nuevaPersona.setEmail(context.formParam("email"));
    nuevaPersona.setWhatsapp(Integer.parseInt(context.formParam("celular")));
    nuevaPersona.setUsuario(usuario);

    repoUsuario.guardar(usuario);
    repoPersona.guardar(nuevaPersona);

    context.sessionAttribute("logueado", true);
    context.sessionAttribute("persona", nuevaPersona);
    context.redirect("/");

  }
}
