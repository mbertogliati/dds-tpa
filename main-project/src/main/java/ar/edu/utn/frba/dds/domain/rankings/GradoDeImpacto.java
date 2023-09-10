package ar.edu.utn.frba.dds.domain.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

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
