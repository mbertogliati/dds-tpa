package ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;
import java.io.Serializable;

public interface EstrategiaMomentoNotificacion extends Serializable {
    public void notificar(Notificable notificable, Persona persona);
}
