package ar.edu.utn.frba.dds.controllers.middleware;

import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class AuthMiddleware implements Handler {
  public void handle(@NotNull Context ctx){
      if(requiereAutenticacion(ctx.path()) && ctx.sessionAttribute("usuario") == null){
        ctx.status(401).result("Unauthorized");
        ctx.sessionAttribute("msg",new MensajeVista(MensajeVista.TipoMensaje.WARNING, "Debes iniciar sesión para realizar esa acción."));
        ctx.redirect("/login");
      }
  }
  private static boolean requiereAutenticacion(String path){
    return
        !(//NO REQUIEREN AUTENTICACIÓN
               path.equals("/login")
            || path.equals("/register")
            || path.startsWith("/obtener")
            || path.startsWith("/noAuth")
        );
  }
}
