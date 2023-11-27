package ar.edu.utn.frba.dds.controllers.middleware;

import ar.edu.utn.frba.dds.controllers.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class SelfUserMiddleware implements Handler {
    //Si la ruta requiere de {idUsuario}, ese usuario debe ser el de la sesion.
    @Override
    public void handle(@NotNull Context context) throws Exception {
        try {
            int idUsuario = Integer.parseInt(context.pathParam("idUsuario"));
            Usuario usuario = context.sessionAttribute("usuario");
            assert usuario != null;
            if (idUsuario != usuario.getId()) {
                throw new UnauthorizedException("No tiene permisos para realizar esta acción");
            }
        }
        catch (NumberFormatException ignored){
        }
    }
}
