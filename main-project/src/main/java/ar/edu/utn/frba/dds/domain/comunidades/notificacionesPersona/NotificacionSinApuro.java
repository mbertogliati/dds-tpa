package ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.notificaciones.Notificable;


public class NotificacionSinApuro implements EstrategiaMomentoNotificacion {
    @Override
    public void notificar(Notificable notificable, Persona persona) {
        persona.getNotificablesSinNotificar().agregarNotificables(notificable);
    }
}
