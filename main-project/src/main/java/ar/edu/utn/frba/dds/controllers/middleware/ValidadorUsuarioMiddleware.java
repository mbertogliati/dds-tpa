package ar.edu.utn.frba.dds.controllers.middleware;

import ar.edu.utn.frba.dds.controllers.exceptions.ValidacionUsuarioException;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.hasheo.EstrategiaHash;
import ar.edu.utn.frba.dds.modelos.hasheo.HashPBKDF2;
import ar.edu.utn.frba.dds.modelos.validacion.*;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

public class ValidadorUsuarioMiddleware implements Handler {
    private static ValidadorUsuario validador;

    static{
        ValidadorUsuarioConcreto validadorConcreto = new ValidadorUsuarioConcreto();
        validadorConcreto.agregarEstrategias(
                new EstrategiaValidacionRegExp("[A-Z]"),
                new EstrategiaValidacionRegExp("[a-z]"),
                new EstrategiaValidacionRegExp("[0-9]"),
                new EstrategiaValidacionRegExp("[#?!@$ %^&*-]"),
                new EstrategiaValidacionRegExp(".{8,}"),
                new EstrategiaValidacionNoEstaEnLista(ObtenerTopPeoresPasswords.instancia())
        );
        validador = validadorConcreto;
    }
    @Override
    public void handle(@NotNull Context context) throws Exception {
        if(context.method().name().equals("GET")){
            return;
        }
        if(this.usuarioValido(context)) {
            return;
        }

        throw new ValidacionUsuarioException("Datos no válido. Revise los datos ingresados");
    }
    private boolean usuarioValido(Context context){
        return !context.formParam("password").equals(context.formParam("repetir_password"))
                && passwordValida(context)
                && emailValido(context);
    }
    private boolean passwordValida(Context context){
        Usuario usuario = new Usuario();
        usuario.setUsername(context.formParam("username"));
        return validador.validar(usuario,context.formParam("password"));
    }
    private boolean emailValido(Context context){
        return context.formParam("email").matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
}
