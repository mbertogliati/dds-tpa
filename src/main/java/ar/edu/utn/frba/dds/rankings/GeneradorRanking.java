package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class GeneradorRanking {

    @Getter @Setter
    private GeneradorPuntos generadorPuntos;
    @Getter @Setter
    private String descripcion;

    public Ranking generarRanking(List<IncidentePorComunidad> incidentes) {
        Ranking ranking = new Ranking();

        generadorPuntos.generarPuntos(incidentes).forEach((entidad, cantidad) -> {
            ranking.agregarEntidad(entidad, (double) cantidad);
        });

        ranking.ordernar();
        ranking.setFechaHoraCreacion(LocalDateTime.now());
        ranking.setDescripcion("Ranking de entidades con mas incidentes en la semana");

        return ranking;
    }
}
