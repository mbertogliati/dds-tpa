package ar.edu.utn.frba.dds.modelos.notificaciones;

public class NoExisteMetodoExcepcion extends RuntimeException {
    public NoExisteMetodoExcepcion() {
        super("No existe el método de notificación indicado");
    }
}
