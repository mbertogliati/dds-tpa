package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.ProvinciaRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class CierreIncidenteController implements Handler {
  ProvinciaRepositorio repoProvincia;

  public CierreIncidenteController(EntityManager entityManager){
    this.repoProvincia = new ProvinciaRepositorio(entityManager);
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    if(VerificadorLogueo.noEstaLogueado(context.sessionAttribute("logueado"))){
      context.redirect("/login");
      return;
    }

    Map<String, Object> model = new HashMap<>();

    List<Provincia> provincias = repoProvincia.buscarTodas();

    model.put("provincias", provincias);

    context.render("cierreIncidente.hbs", model);
  }
}
