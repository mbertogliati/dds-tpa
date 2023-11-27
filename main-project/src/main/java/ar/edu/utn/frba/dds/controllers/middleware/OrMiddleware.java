package ar.edu.utn.frba.dds.controllers.middleware;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class OrMiddleware implements Handler {
    private Handler[] handlers;
    private Exception exception;
    public OrMiddleware(Exception exception,Handler ... handlers){
        this.handlers = handlers;
        this.exception = exception;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        for (Handler handler : this.handlers) {
            try {
                handler.handle(context);
                return;
            } catch (Exception e) {
                // No hago nada, sigo con el siguiente
            }
        }
        throw exception;
    }
}
