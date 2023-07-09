package ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.notificaciones.Notificable;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class NotificacionSinApuro implements EstrategiaMomentoNotificacion {
    private ListadoNotificables notificablesSinNotificar;
    private Map<DayOfWeek, List<LocalTime>> fechas;

    public void notificar(Notificable notificable, Persona persona) {
        notificablesSinNotificar.agregarNotificables(notificable);
    }
    public void enviarNotificacion(){

    }
}
/*

 */
