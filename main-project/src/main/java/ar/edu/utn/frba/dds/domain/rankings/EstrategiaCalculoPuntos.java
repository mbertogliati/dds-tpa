package ar.edu.utn.frba.dds.domain.rankings;

import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import java.util.List;

public interface EstrategiaCalculoPuntos {
    public List<PuntosPorEntidad> calcularPuntos(List<IncidentePorComunidad> incidentes);
}
