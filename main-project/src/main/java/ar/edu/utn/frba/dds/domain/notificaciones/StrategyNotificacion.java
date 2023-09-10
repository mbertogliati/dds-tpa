package ar.edu.utn.frba.dds.domain.notificaciones;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;

public interface StrategyNotificacion {
    public void enviarNotificacion(String mensaje, Persona persona);
}
