package ar.edu.utn.frba.controller;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class RechazarFusionController implements Handler {

  public RechazarFusionController(){
    super();
  }

  @Override
  public void handle(Context context) throws Exception {
    context.json("Fusión rechazada correctamente.");
  }
}
