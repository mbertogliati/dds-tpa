package ar.edu.utn.frba.dds.controllers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class IndexController implements Handler {

  @Override
  public void handle(@NotNull Context context) throws Exception {
    context.render("index.hbs");
  }
}
