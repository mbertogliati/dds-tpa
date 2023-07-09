package ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.notificaciones.Notificable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListadoNotificables implements Notificable {

    private Map<Notificable, LocalDateTime> notificables;

    public ListadoNotificables() {
        this.notificables = new HashMap<Notificable, LocalDateTime>();
    }

    public void agregarNotificables(Notificable ... notificablesRecibidos) {
        for(Notificable unNotificable : notificablesRecibidos) {
            this.notificables.put(unNotificable, LocalDateTime.now());
        }
    }

    private List<Notificable> obtenerNotificablesDelDia() {
        List<Notificable> filtrados = new ArrayList<Notificable>();
        for(Notificable unNotificable : this.notificables.keySet()) {
            LocalDateTime fechaNotificable = this.notificables.get(unNotificable);
            if(fechaNotificable.plusDays(1).isAfter(LocalDateTime.now())) {
                filtrados.add(unNotificable);
            }
        }
        return filtrados;
    }

    public void vaciarNotificables(){
        this.notificables.clear();
    }

    @Override
    public String getInfo() {
        String info = "";
        for(Notificable notificable : this.obtenerNotificablesDelDia()) {
            info = info + notificable.getInfo() + "\n";
        }
        return info;
    }

    public boolean estaVacio() {
        return this.notificables.isEmpty();
    }
}
