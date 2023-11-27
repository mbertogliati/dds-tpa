package ar.edu.utn.frba.dds.controllers.middleware;

import io.javalin.http.Handler;

public class AndMiddleware implements Handler{
    private Handler[] handlers;
    public AndMiddleware(Handler ... handlers){
        this.handlers = handlers;
    }

    @Override
    public void handle(io.javalin.http.Context context) throws Exception {
        for (Handler handler : this.handlers) {
            handler.handle(context);
        }
    }

}
