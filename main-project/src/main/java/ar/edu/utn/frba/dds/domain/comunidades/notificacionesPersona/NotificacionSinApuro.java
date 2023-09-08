package ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.notificaciones.Notificable;

import ar.edu.utn.frba.dds.notificaciones.Notificador;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Dictionary;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class NotificacionSinApuro implements EstrategiaMomentoNotificacion {
    @Setter
    private Persona persona;

    public NotificacionSinApuro() {
    }

    @Override
    public void notificar(Notificable notificable, Persona persona) {
        if(this.persona == null) {
            this.persona = persona;
        }
        this.persona.getNotificablesSinNotificar().agregarNotificables(notificable);
    }

    public void enviarNotificaciones() {
        if(!this.persona.getNotificablesSinNotificar().estaVacio()) {
            Notificador.notificar(this.persona.getNotificablesSinNotificar(), this.persona);
            this.persona.getNotificablesSinNotificar().vaciarNotificables();
        }
    }
}
