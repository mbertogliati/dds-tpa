package ar.edu.utn.frba.dds.notificaciones;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;

public interface StrategyNotificacion {
    public void enviarNotificacion(String mensaje, Persona persona);
}
