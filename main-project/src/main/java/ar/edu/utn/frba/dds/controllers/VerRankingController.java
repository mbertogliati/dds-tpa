package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.repositorios.rankings.RankingRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class VerRankingController implements Handler {
  private RankingRepositorio repoRankings;

  public VerRankingController(EntityManager entityManager){
    this.repoRankings = new RankingRepositorio(entityManager);
  }
  @Override
  public void handle(@NotNull Context context) throws Exception {
    Map<String, Object> model = new HashMap<>();

    String idRanking = context.pathParam("id");

    model.put("ranking",this.repoRankings.obtenerRankingPorId(Integer.parseInt(idRanking)));
    context.render("verRanking.hbs", model);
  }
}