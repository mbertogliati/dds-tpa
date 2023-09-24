package ar.edu.utn.frba.dds.modelos.incidentes;

import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;

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
