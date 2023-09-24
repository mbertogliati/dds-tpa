package ar.edu.utn.frba.dds.modelos.incidentes;

import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;
import lombok.Getter;

public class RevisionIncidente implements Notificable {
    @Getter
    private Incidente incidente;

    public RevisionIncidente(Incidente incidente){
        this.incidente = incidente;
    }

    @Override
    public String getInfo() {
        return "Solicitamos que revise el incidente: " + this.incidente.getInfo();
    }
}
