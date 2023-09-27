package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class AdministrarUsuarioController implements Handler {
  private PersonaRepositorio repoPersona;

  public AdministrarUsuarioController(EntityManager entityManager){
    repoPersona = new PersonaRepositorio(entityManager);
  }
  @Override
  public void handle(@NotNull Context context) throws Exception {
    if(VerificadorLogueo.noEstaLogueado(context.sessionAttribute("logueado"))){
      context.redirect("/login");
      return;
    }

    Persona persona = context.sessionAttribute("persona");
    if (!VerificadorRol.tieneRol(persona, VerificadorRol.Permiso.ADMINISTRAR_USUARIOS)){
      context.redirect("/");
      return;
    }
    
    Map<String,Object> model = new HashMap<>();

    String id = context.pathParam("id");

    model.put("persona", repoPersona.buscarPorId(Integer.parseInt(id)));
    context.render("administrarUsuario.hbs",model);
  }
}
