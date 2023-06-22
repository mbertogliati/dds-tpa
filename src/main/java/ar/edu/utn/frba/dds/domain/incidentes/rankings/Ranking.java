package ar.edu.utn.frba.dds.domain.incidentes.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.notificaciones.Notificable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Ranking implements Notificable {
    @Getter @Setter
    private Ponderador ponderador;
    @Getter @Setter
    private List<Entidad> entidades;

    @Override
    public String getInfo() {
        return null;
    }
}
