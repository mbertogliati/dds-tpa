package ar.edu.utn.frba.dds.controllers.exceptions.handlers;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;

import java.util.Map;

public class ExceptionHandler implements io.javalin.http.ExceptionHandler<Exception>{
    @Override
    public void handle(Exception e, io.javalin.http.Context context) {
        Map<String, Object> model = GeneradorModel.model(context);
        context.status(500);
        model.put("statusCode", 500);
        model.put("statusCodeDescription", "Internal Server Error");
        model.put("mensaje", "Parece ha habido un problema con la aplicación. Por favor, intente nuevamente más tarde.");
        model.put("excepcion", e.getMessage());
        context.render("httpStatus.hbs", model);
    }
}
