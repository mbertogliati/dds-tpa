package ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;


public class NotificacionSinApuro implements EstrategiaMomentoNotificacion {
    @Override
    public void notificar(Notificable notificable, Persona persona) {
        persona.getNotificablesSinNotificar().agregarNotificables(notificable);
    }
}
