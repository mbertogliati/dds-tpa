package ar.edu.utn.frba.dds.controllers.utils;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
@Getter
@Setter
public class HttpErrorHandler implements Handler {
    private int statusCode= 500;
    private String statusCodeDescription = "Internal Server Error";
    private String mensaje = "Parece ha habido un problema con la aplicación. Por favor, intente nuevamente más tarde.";
    private Exception e;

    public HttpErrorHandler() {
    }
    public HttpErrorHandler(int statusCode, String statusCodeDescription, String mensaje) {
        this.statusCode = statusCode;
        this.statusCodeDescription = statusCodeDescription;
        this.mensaje = mensaje;
    }
    public HttpErrorHandler(int statusCode, String statusCodeDescription, String mensaje, Exception e) {
        this.statusCode = statusCode;
        this.statusCodeDescription = statusCodeDescription;
        this.mensaje = mensaje;
        this.e = e;
    }
    @Override
    public void handle(@NotNull Context context){
        Map<String, Object> model = GeneradorModel.model(context);
        context.status(this.statusCode);
        model.put("statusCode", this.statusCode);
        model.put("statusCodeDescription", this.statusCodeDescription);
        model.put("mensaje", this.mensaje);
        model.put("excepcion", e);
        context.render("httpStatus.hbs", model);
    }
}
