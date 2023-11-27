package ar.edu.utn.frba.dds.controllers.exceptions.handlers;

import ar.edu.utn.frba.dds.controllers.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.HttpStatus;

public class UnauthorizedHandler implements ExceptionHandler<UnauthorizedException> {
        private static final String defaultPath = "/home?error";
        private static final MensajeVista.TipoMensaje defaultTipoMensaje = MensajeVista.TipoMensaje.ERROR;
        @Override
        public void handle(UnauthorizedException e, io.javalin.http.Context context) {
            context.sessionAttribute("msg", new MensajeVista(e.getTipoMensaje() != null ? e.getTipoMensaje() : defaultTipoMensaje, e.getMessage()));
            context.status(HttpStatus.UNAUTHORIZED);
            context.redirect(e.getPath() != null ? e.getPath() : defaultPath);
        }
}
