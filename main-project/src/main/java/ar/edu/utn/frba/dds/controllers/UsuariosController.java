package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public class UsuariosController implements Handler {
  private PersonaRepositorio repoPersona;
  public UsuariosController(EntityManager entityManager){
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

    Map<String, Object> model = new HashMap<>();

    String param = context.queryParam("username");
    List<Persona> listaPersonas;

    if(param != null){
      listaPersonas = repoPersona.buscarQueTenganUsername(param);
    }else{
      listaPersonas = repoPersona.buscarTodas();
    }

    model.put("personas", listaPersonas);

    context.render("usuarios.hbs", model);
  }
}
