package ar.edu.utn.frba.dds.domain.incidentes.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;

import java.util.List;

public class MasIncidentesEnSemana implements Ponderador{
    @Override
    public List<Entidad> ordenar(List<Entidad> entidades) {
        //TODO: IMPLEMENTAR ORDENAMIENTO
        return entidades;
    }
}
