package ar.edu.utn.frba.dds.domain.incidentes;

import ar.edu.utn.frba.dds.notificaciones.Notificable;

public class IncidenteAbierto implements Notificable {
    private Incidente incidente;

    public IncidenteAbierto(Incidente incidente){
        this.incidente = incidente;
    }

    @Override
    public String getInfo() {
        return "Se abrió el incidente: " + this.incidente.getInfo();
    }
}
