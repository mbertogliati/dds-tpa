package ar.edu.utn.frba.dds.domain.incidentes;

import ar.edu.utn.frba.dds.notificaciones.Notificable;
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
