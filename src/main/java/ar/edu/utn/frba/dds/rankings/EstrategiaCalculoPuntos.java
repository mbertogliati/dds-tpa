package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;

import java.util.List;
import java.util.Map;

public interface EstrategiaCalculoPuntos {
    public Map<Entidad,Double> calcularPuntos(List<IncidentePorComunidad> incidentes);
}
