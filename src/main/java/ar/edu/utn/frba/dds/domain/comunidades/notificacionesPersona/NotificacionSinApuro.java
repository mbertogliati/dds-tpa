package ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.notificaciones.Notificable;

import ar.edu.utn.frba.dds.notificaciones.Notificador;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class NotificacionSinApuro implements EstrategiaMomentoNotificacion {
    @Getter
    private ListadoNotificables notificablesSinNotificar;

    @Getter
    private Map<DayOfWeek, List<LocalTime>> fechas;

    @Setter
    private Persona persona;

    public NotificacionSinApuro() {
        this.notificablesSinNotificar = new ListadoNotificables();
        this.fechas = new HashMap<DayOfWeek, List<LocalTime>>();
    }

    @Override
    public void notificar(Notificable notificable, Persona persona) {
        if(this.persona == null) {
            this.persona = persona;
        }
        this.notificablesSinNotificar.agregarNotificables(notificable);
    }

    public void enviarNotificaciones() {
        if(!this.notificablesSinNotificar.estaVacio()) {
            Notificador.notificar(this.notificablesSinNotificar, this.persona);
            this.notificablesSinNotificar.vaciarNotificables();
        }
    }
}
