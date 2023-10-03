package ar.edu.utn.frba.dds.controllers.exceptions;

public class FormInvalidoException extends RuntimeException{
    public FormInvalidoException(String mensaje){
        super(mensaje);
    }
}
