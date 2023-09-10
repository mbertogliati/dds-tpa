package ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.notificaciones.Notificable;
import ar.edu.utn.frba.dds.domain.notificaciones.Notificador;

public class NotificacionAlMomento implements EstrategiaMomentoNotificacion {
    @Override
    public void notificar(Notificable notificable, Persona persona) {
        Notificador.notificar(notificable, persona);
    }
}
