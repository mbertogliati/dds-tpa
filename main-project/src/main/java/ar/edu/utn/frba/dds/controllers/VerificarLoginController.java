package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.hasheo.EstrategiaHash;
import ar.edu.utn.frba.dds.modelos.hasheo.HashPBKDF2;
import ar.edu.utn.frba.dds.modelos.validacion.ValidadorUsuario;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;

public class VerificarLoginController implements Handler {
  PersonaRepositorio personaRepositorio;
  UsuarioRepositorio usuarioRepositorio;
  EstrategiaHash hasheador;

  public VerificarLoginController(EntityManager entityManager) {
    this.personaRepositorio = new PersonaRepositorio(entityManager);
    this.usuarioRepositorio = new UsuarioRepositorio(entityManager);
    this.hasheador = new HashPBKDF2();
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    String username = context.formParam("username");
    String password = context.formParam("password");

    if(usuarioRepositorio.buscarPorUsername(username).isEmpty()){
      context.sessionAttribute("logueado", null);
      context.redirect("/login?error=logueo");
      return;
    }

    Persona persona = personaRepositorio.buscarPorUsername(username).get(0);

    if(Objects.equals(hasheador.hashear(password), persona.getUsuario().getPassword())){
      context.sessionAttribute("logueado", true);
      context.sessionAttribute("persona", persona);
      context.redirect("/");
    }else{
      context.sessionAttribute("logueado", null);
      context.redirect("/login?error=logueo");
    }

    return;
  }
}
