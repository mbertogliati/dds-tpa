package ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificador;

public class NotificacionAlMomento implements EstrategiaMomentoNotificacion {
    @Override
    public void notificar(Notificable notificable, Persona persona) {
        persona.getNotificablesAlMomento().add(new NotificablesParaCronTaskAlMomento(notificable, persona));
    }
}
