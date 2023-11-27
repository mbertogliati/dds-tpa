package ar.edu.utn.frba.dds.controllers.exceptions;

public class ExternalException extends RuntimeException{
    public ExternalException(String message) {
        super(message);
    }
}
