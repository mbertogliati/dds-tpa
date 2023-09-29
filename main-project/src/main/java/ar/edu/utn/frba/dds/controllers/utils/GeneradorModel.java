package ar.edu.utn.frba.dds.controllers.utils;

import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;

public class GeneradorModel {
  public static Map<String, Object> model(Context context){
    Map<String, Object> model = new HashMap<>();

    model.put("userActual", context.sessionAttribute("usuario"));

    String path = context.path().split("/")[1];
    String navSelected;
    switch(path){
      case "home":
        navSelected = "nav-index";
        break;
      case "incidentes":
        navSelected = "nav-incidentes";
        break;
      case "aperturaIncidente":
        navSelected = "nav-incidentes";
        break;
      case "cierreIncidente":
        navSelected = "nav-incidentes";
        break;
      case "rankings":
        navSelected = "nav-entidades";
        break;
      case "cargaMasiva":
        navSelected = "nav-entidades";
        break;
      case "entidadesPrestadoras":
        navSelected = "nav-entidades";
        break;
      case "entidades":
        navSelected = "nav-entidades";
        break;
      case "establecimientos":
        navSelected = "nav-entidades";
        break;
      case "servicios":
        navSelected = "nav-entidades";
        break;
      case "usuarios":
        navSelected = "nav-usuarios";
        break;
      case "comunidades":
        navSelected = "nav-comunidades";
        break;
      case "misComunidades":
        navSelected = "nav-comunidades";
        break;
      default:
        navSelected = "";
        break;
    }
    model.put(navSelected, true);

    if(context.sessionAttribute("adminPlataforma") != null){
      model.put("adminPlataforma", true);
    }

    return model;
  }
}
