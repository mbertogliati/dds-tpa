package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradoDeImpacto implements EstrategiaCalculoPuntos {

    @Getter @Setter
    private MedidorImpacto medidorImpacto;

    @Override
    public Map<Entidad,Double> calcularPuntos(List<IncidentePorComunidad> incidentes) {

        Map<Entidad,Double> diccionarioImpactos = new HashMap<>();

        incidentes.forEach(incidentePorComunidad -> {
            Entidad entidad = incidentePorComunidad.getIncidente().obtenerEntidad();
            diccionarioImpactos.put(entidad, medidorImpacto.medirImpactoDe(entidad, incidentes));
        });

        return diccionarioImpactos;
    }
}
