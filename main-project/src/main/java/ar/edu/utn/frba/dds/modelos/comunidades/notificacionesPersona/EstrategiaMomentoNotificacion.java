package ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;

public interface EstrategiaMomentoNotificacion {
    public void notificar(Notificable notificable, Persona persona);
}
