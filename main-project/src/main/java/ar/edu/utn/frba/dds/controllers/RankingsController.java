package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.rankings.Ranking;
import ar.edu.utn.frba.dds.repositorios.rankings.RankingRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.List;
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

    String param = context.queryParam("fecha");
    List<Ranking> listaRankings;

    if(param != null){
      listaRankings = repoRankings.buscarConFechaCreacionPosteriorA(param);
    }else{
      listaRankings = repoRankings.obtenerTodos();
    }

    model.put("rankings", listaRankings);

    context.render("rankingsEntidades.hbs", model);
  }
}
