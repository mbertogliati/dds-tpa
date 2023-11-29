package ar.edu.utn.frba.dds.controllers.generales.user;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class IndexController implements Handler {

  @Override
  public void handle(@NotNull Context context) throws Exception {
    Map<String, Object> model = GeneradorModel.model(context);

    context.render("index.hbs", model);
  }
}
