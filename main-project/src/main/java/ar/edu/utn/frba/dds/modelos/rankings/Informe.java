package ar.edu.utn.frba.dds.modelos.rankings;

import ar.edu.utn.frba.dds.modelos.entidades.Informable;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Informe implements Notificable {
    @Getter @Setter
    private Informable informable;
    @Getter
    private List<Ranking> rankings = Collections.unmodifiableList(new ArrayList<>());

    public String getInfo() {
        StringBuilder info = new StringBuilder("Informe de " + informable.getNombre() + ":\n");
        for(Ranking ranking : rankings){
            info.append(ranking.toString()).append("\n");
        }
        return info.toString();
    }

    public void agregarRankings(Ranking ... listaRankings) {
        List<Ranking> rankingsActuales = new ArrayList<>(rankings);

        List<Ranking> rankingsFiltrados = Arrays.stream(listaRankings)
                .map(ranking ->
                        ranking.filtrar(puntosPorEntidad ->
                                informable.getEntidades().contains(puntosPorEntidad.getEntidad()) )).toList();

        rankingsActuales.addAll(rankingsFiltrados);
        rankings = Collections.unmodifiableList(rankingsActuales);
    }

}
