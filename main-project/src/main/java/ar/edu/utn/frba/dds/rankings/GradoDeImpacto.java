package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradoDeImpacto implements EstrategiaCalculoPuntos {

    @Getter @Setter
    private MedidorImpacto medidorImpacto;

    @Override
    public List<PuntosPorEntidad> calcularPuntos(List<IncidentePorComunidad> incidentes) {

        List<PuntosPorEntidad> puntos = new ArrayList<PuntosPorEntidad>();

        incidentes.forEach(incidentePorComunidad -> {
            Entidad entidad = incidentePorComunidad.getIncidente().obtenerEntidad();
            puntos.add(new PuntosPorEntidad(entidad, medidorImpacto.medirImpactoDe(entidad, incidentes)));
        });

        return puntos;
    }
}
