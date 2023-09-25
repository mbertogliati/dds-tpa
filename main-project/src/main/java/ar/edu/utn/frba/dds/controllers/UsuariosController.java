package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class UsuariosController implements Handler {
  private PersonaRepositorio repoPersona;
  public UsuariosController(EntityManager entityManager){
    repoPersona = new PersonaRepositorio(entityManager);
  }
  @Override
  public void handle(@NotNull Context context) throws Exception {
    Map<String, Object> model = new HashMap<>();

    model.put("personas", repoPersona.buscarTodas());

    context.render("usuarios.hbs", model);
  }
}
