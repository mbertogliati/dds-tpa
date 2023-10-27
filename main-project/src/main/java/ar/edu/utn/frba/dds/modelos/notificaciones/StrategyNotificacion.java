package ar.edu.utn.frba.dds.modelos.notificaciones;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;

public interface StrategyNotificacion {
    public void enviarNotificacion(String mensaje, Persona persona);
}
