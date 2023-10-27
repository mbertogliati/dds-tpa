package ar.edu.utn.frba.dds.modelos.rankings;

import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import java.util.List;

public interface EstrategiaCalculoPuntos {
    public List<PuntosPorEntidad> calcularPuntos(List<IncidentePorComunidad> incidentes);
}
