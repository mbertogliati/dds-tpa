package ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.notificaciones.Notificable;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public class NotificacionSinApuro implements EstrategiaMomentoNotificacion {
    private ListadoNotificables notificablesSinNotificar;
    private Map<DayOfWeek, List<Horario>> fechas;

    @Override
    public void notificar(Notificable notificable, Persona persona) {

    }
}
