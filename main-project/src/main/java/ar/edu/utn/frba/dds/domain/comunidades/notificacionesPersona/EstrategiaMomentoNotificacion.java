package ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.notificaciones.Notificable;

public interface EstrategiaMomentoNotificacion {
    public void notificar(Notificable notificable, Persona persona);
}
