package ar.edu.utn.frba.dds.controllers.exceptions.handlers;

import ar.edu.utn.frba.dds.controllers.exceptions.ExternalException;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import io.javalin.http.Context;
import io.javalin.http.ExceptionHandler;
import org.jetbrains.annotations.NotNull;

public class ExternalExceptionHandler implements ExceptionHandler<ExternalException> {

    @Override
    public void handle(@NotNull ExternalException e, @NotNull Context context) {
        context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.ERROR, e.getMessage()));
        context.redirect("home?error");
    }
}
