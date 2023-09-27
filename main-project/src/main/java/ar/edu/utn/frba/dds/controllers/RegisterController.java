package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.modelos.validacion.EstrategiaValidacionNoEstaEnLista;
import ar.edu.utn.frba.dds.modelos.validacion.EstrategiaValidacionRegExp;
import ar.edu.utn.frba.dds.modelos.validacion.ObtenerTopPeoresPasswords;
import ar.edu.utn.frba.dds.modelos.validacion.ValidadorUsuario;
import ar.edu.utn.frba.dds.modelos.validacion.ValidadorUsuarioConcreto;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class RegisterController implements Handler {



  @Override
  public void handle(@NotNull Context context) throws Exception {
    String param = context.queryParam("error");

    if(param != null){
      Map<String, Object> model = new HashMap<>();
      model.put("error", "Revise sus datos");
      context.render("register.hbs", model);
    }else{
      context.render("register.hbs");
    }
    return;

  }
}
