package ar.edu.utn.frba.dds.controllers.utils;

import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;

public class GeneradorModel {
  public static Map<String, Object> model(Context context){
    Map<String, Object> model = new HashMap<>();

    model.put("userActual", context.sessionAttribute("usuario"));
    String path = "";
    try {
      path = context.path().split("/")[1];
    }
    catch (Exception e){
      //ignorar
    }
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
        navSelected = "nav-servicios";
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
      model.put("manejador", true);
    }else if(context.sessionAttribute("comunidad") != null) {
      model.put("comunidad", context.sessionAttribute("comunidad"));

      if (context.sessionAttribute("adminComunidad") != null) {
        model.put("adminComunidad", true);
      }
    } else if (context.sessionAttribute("organismoControl") != null) {
      model.put("organismoControl", context.sessionAttribute("organismoControl"));
      model.put("manejador", true);
    } else if (context.sessionAttribute("entidadPrestadora") != null) {
      model.put("entidadPrestadora", context.sessionAttribute("entidadPrestadora"));
      model.put("manejador", true);
    } else{
      model.put("userDefault", true);
    }
    model.put("rolSeleccionado", context.sessionAttribute("rolSeleccionado"));
    model.put("msg", context.consumeSessionAttribute("msg"));

    return model;
  }
}
