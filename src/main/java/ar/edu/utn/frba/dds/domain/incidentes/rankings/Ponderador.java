package ar.edu.utn.frba.dds.domain.incidentes.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;

import java.util.List;

public interface Ponderador {
    public List<Entidad> ordenar(List<Entidad> entidades);
}
