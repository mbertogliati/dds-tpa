package ar.edu.utn.frba.dds.domain.incidentes;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class IncidentePorComunidad {
    @Getter
    private Incidente incidente;
    @Getter @Setter
    private boolean estaCerrado = false;
    @Getter @Setter
    private LocalDateTime fechaHoraCierre = null;
    @Getter @Setter
    private Persona autorCierre = null;

    public IncidentePorComunidad(Incidente incidente){
        this.incidente = incidente;
    }
}
