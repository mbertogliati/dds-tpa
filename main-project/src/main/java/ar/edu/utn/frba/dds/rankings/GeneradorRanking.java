package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

//TODO: Evaluar si esta clase tiene que ser estatica con instancias bien conocidas
//Ej La clase Icono en el enunciado de tendenciasMusicales
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
