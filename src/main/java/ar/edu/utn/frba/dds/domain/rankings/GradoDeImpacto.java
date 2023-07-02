package ar.edu.utn.frba.dds.domain.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class GradoDeImpacto implements GeneradorRanking {

    @Getter @Setter
    private MedidorImpacto medidorImpacto;

    @Override
    public Ranking generarRanking(List<IncidentePorComunidad> incidentes) {

        Ranking ranking = new Ranking();

        incidentes.forEach(incidentePorComunidad -> {
            Entidad entidad = incidentePorComunidad.getIncidente().obtenerEntidad();
            ranking.agregarEntidad(entidad, medidorImpacto.medirImpactoDe(entidad, incidentes));
        });

        ranking.setDescripcion("Ranking de entidades con mayor grado de impacto");
        ranking.setFechaHoraCreacion(LocalDateTime.now());

        return ranking;
    }
}
