package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
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

public class RankingsController implements ICrudViewsHandler {
  private RankingRepositorio repoRankings;

  public RankingsController(EntityManager entityManager){

    this.repoRankings = new RankingRepositorio(entityManager);
  }
  @Override
  public void index(@NotNull Context context){

    Map<String, Object> model = GeneradorModel.model(context);

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

  @Override
  public void show(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);



    String idRanking = context.pathParam("id");

    model.put("ranking",this.repoRankings.obtenerRankingPorId(Integer.parseInt(idRanking)));
    context.render("verRanking.hbs", model);
  }

  @Override
  public void create(Context context) {

  }

  @Override
  public void save(Context context) {

  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) {

  }

  @Override
  public void delete(Context context) {

  }
}
