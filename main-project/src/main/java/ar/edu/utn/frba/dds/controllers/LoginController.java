package ar.edu.utn.frba.dds.controllers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class LoginController implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {
    String param = context.queryParam("error");

    if(param != null){
      Map<String, Object> model = new HashMap<>();
      model.put("error", "Hay error de logueo");
      context.render("login.hbs", model);
    }else{
      context.render("login.hbs");
    }
    return;

  }
}
