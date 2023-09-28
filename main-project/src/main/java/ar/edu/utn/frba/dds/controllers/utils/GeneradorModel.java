package ar.edu.utn.frba.dds.controllers.utils;

import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;

public class GeneradorModel {
  public static Map<String, Object> model(Context context){
    Map<String, Object> model = new HashMap<>();

    model.put("userActual", context.sessionAttribute("usuario"));

    return model;
  }
}
