package ar.edu.utn.frba.dds.controllers.exceptions.handlers;

import ar.edu.utn.frba.dds.controllers.exceptions.FormInvalidoException;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class FormInvalidoHandler implements ExceptionHandler<FormInvalidoException> {

    @Override
    public void handle(@NotNull FormInvalidoException e, @NotNull Context context) {
        context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.ERROR, e.getMessage()));
        context.redirect(context.path() + "?error");
    }
}
