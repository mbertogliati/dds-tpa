package ar.edu.utn.frba.dds.controllers.exceptions;

import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import lombok.Getter;
import lombok.Setter;


public class UnauthorizedException extends RuntimeException{
    @Getter
    @Setter
    private MensajeVista.TipoMensaje tipoMensaje = null;
    @Getter
    @Setter
    private String path = null;

    public UnauthorizedException(String message, MensajeVista.TipoMensaje tipoMensaje, String path){
        super(message);
        this.tipoMensaje = tipoMensaje;
        this.path = path;
    }
    public UnauthorizedException(String message, MensajeVista.TipoMensaje tipoMensaje){
        super(message);
        this.tipoMensaje = tipoMensaje;
    }
    public UnauthorizedException(String message){
        super(message);
    }
}
