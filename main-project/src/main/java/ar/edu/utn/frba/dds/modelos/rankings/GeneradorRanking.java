package ar.edu.utn.frba.dds.modelos.rankings;

import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class GeneradorRanking {

    @Getter @Setter
    private EstrategiaCalculoPuntos generadorPuntos;

    public Ranking generarRanking(List<IncidentePorComunidad> incidentes, String descripcion) {
        Ranking ranking = new Ranking();

        generadorPuntos.calcularPuntos(incidentes).forEach(ranking::agregarEntidad);

        ranking.ordernar();
        ranking.setFechaHoraCreacion(LocalDateTime.now());
        ranking.setDescripcion(descripcion);

        return ranking;
    }
}
