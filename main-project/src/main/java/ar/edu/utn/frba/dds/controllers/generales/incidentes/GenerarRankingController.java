package ar.edu.utn.frba.dds.controllers.generales.incidentes;

import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificador;
import ar.edu.utn.frba.dds.modelos.rankings.*;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidentePorComunidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.rankings.RankingRepositorio;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GenerarRankingController {
    private final GeneradorRanking generadorRanking = new GeneradorRanking();
    private final Notificador notificador = new Notificador();
    private final List<EstrategiaCalculoPuntos> formasDeCalcular = new ArrayList<>(
            List.of(new EstrategiaCalculoPuntos[]{
                    //new GradoDeImpacto(),
                    new MasIncidentes(),
                    new PromedioEntreAperturaYCierre()
            })
            );
    private IncidentePorComunidadRepositorio repoIncidentes;
    private RankingRepositorio repoRanking;

    public GenerarRankingController(EntityManager entityManager) {
        this.repoIncidentes = new IncidentePorComunidadRepositorio(entityManager);
        this.repoRanking = new RankingRepositorio(entityManager);
    }
    public void generarRankingUltimaSemana() {
        System.out.println("[INFO]: Generando ranking de la última semana...");
        List<IncidentePorComunidad> incidentes = obtenerIncidentesDeLaSemana();

        formasDeCalcular.forEach(formaDeCalcular -> {
            generadorRanking.setGeneradorPuntos(formaDeCalcular);
            Ranking ranking = generadorRanking.generarRanking(incidentes, "Ranking del "+ LocalDate.now() + ": " + formaDeCalcular.getClass().getSimpleName());
            repoRanking.guardarRanking(ranking);
        });
        System.out.println("[INFO]: Ranking generado correctamente.");
    }
    private List<IncidentePorComunidad> obtenerIncidentesDeLaSemana(){
        //Obtengo los incidentes de la semana anterior
        return repoIncidentes.buscarTodos()
                .stream()
                .filter(incidente ->
                        incidente.getIncidente().getFechaHoraApertura()
                                .isAfter(LocalDateTime.now().minusWeeks(1)))
                .toList();
    }
}
