package ar.edu.utn.frba.dds.domain.incidentes;

import ar.edu.utn.frba.dds.notificaciones.Notificable;

public class IncidenteCerrado implements Notificable {
    private Incidente incidente;

    public IncidenteCerrado(Incidente incidente){
        this.incidente = incidente;
    }

    @Override
    public String getInfo() {
        return "Se cerró el incidente: " + this.incidente.getInfo();
    }
}
