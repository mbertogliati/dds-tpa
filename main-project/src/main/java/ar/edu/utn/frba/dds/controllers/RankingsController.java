package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.repositorios.rankings.RankingRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import org.jetbrains.annotations.NotNull;

public class RankingsController implements Handler {
  private RankingRepositorio repoRankings;

  public RankingsController(EntityManager entityManager){
    this.repoRankings = new RankingRepositorio(entityManager);
  }
  @Override
  public void handle(@NotNull Context context) throws Exception {
    if(VerificadorLogueo.noEstaLogueado(context.sessionAttribute("logueado"))){
      context.redirect("/login");
      return;
    }

    Map<String, Object> model = new HashMap<>();
    //TODO: No traerse los PuntosPorEntidad
    model.put("rankings",this.repoRankings.obtenerTodos());
    context.render("rankingsEntidades.hbs", model);
  }
}
