package ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.notificaciones.Notificable;

import java.util.Collections;
import java.util.List;

public class ListadoNotificables implements Notificable {
    private List<Notificable> notificables;

    public void agregarNotificables(Notificable ... notificablesRecibidos){
        Collections.addAll(this.notificables, notificablesRecibidos);
    }

    public void vaciarNotificables(){
        this.notificables.clear();
    }

    @Override
    public String getInfo() {
        String info = "";

        for(Notificable notificable : this.notificables){
            info = info + notificable.getInfo() + "\n";
        }

        return info;
    }
}
