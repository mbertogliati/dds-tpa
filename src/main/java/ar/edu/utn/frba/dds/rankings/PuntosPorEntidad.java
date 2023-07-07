package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import lombok.Getter;
import lombok.Setter;

public class PuntosPorEntidad {

    PuntosPorEntidad(){

    }
    PuntosPorEntidad(Entidad entidad, Double puntos){
        this.entidad = entidad;
        this.puntos = puntos;
    }

    @Getter @Setter
    private Entidad entidad;
    @Getter @Setter
    private Double puntos;
}
