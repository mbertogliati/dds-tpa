package ar.edu.utn.frba.dds.controllers.middleware;

import ar.edu.utn.frba.dds.controllers.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.modelos.comunidades.*;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AutorizacionMiddleware implements Handler {
    private List<Rol> rolesPlataforma = new ArrayList<>();
    private List<Rol> rolesComunidad = new ArrayList<>();
    private List<Permiso> permisos = new ArrayList<>();
    private Boolean chequearComunidad = false;
    public void handle(Context context) throws Exception {
        Usuario usuarioLogueado = context.sessionAttribute("usuario");
        Persona personaLogueada = usuarioLogueado.getPersonaAsociada();
        UnauthorizedException ex = new UnauthorizedException("No tienes permisos para realizar esa acción.");

        if(this.chequearComunidad) {
            //Chequeamos que el usuario esté en una comunidad y que tenga la comunidad del id especificado en caso de que corresponda

            try { //por si falla el parseo a entero
                int idComunidad = Integer.parseInt(context.pathParam("idComunidad"));
                Membresia membresia = personaLogueada.getMembresias().stream().filter(m -> m.getComunidad().getId() == idComunidad).findFirst().orElse(null);
                if (membresia == null
                        || !this.rolesComunidad.stream().allMatch(rol -> membresia.getRoles().stream().anyMatch(r -> r.getId() == rol.getId()))) {
                    //El usuario no pertenece a la comunidad o no tiene todos los roles necesarios
                    throw ex;
                }

            } catch (IllegalArgumentException e) {
                if(personaLogueada.getMembresias().isEmpty()){
                    //El usuario no pertenece a ninguna comunidad
                    throw ex;
                }
            }
        }

        if(!this.rolesPlataforma.stream().allMatch(rol -> usuarioLogueado.getRoles().stream().anyMatch(r -> r.getId() == rol.getId()))){
            //El usuario no tiene todos los roles de platadorma necesarios
            throw ex;
        }
    }
}
