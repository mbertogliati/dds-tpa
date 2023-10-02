package ar.edu.utn.frba.dds.controllers.exceptions;

public class ValidacionUsuarioException extends RuntimeException{
    public ValidacionUsuarioException(String mensaje){
        super(mensaje);
    }
}
