package ar.edu.utn.frba.dds.domain.incidentes.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;

import java.util.List;

public class PromedioEntreCierre implements GeneradorRanking {
    @Override
    public List<Entidad> ordenar(List<Entidad> entidades) {
        return entidades;
    }
}
