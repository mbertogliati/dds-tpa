package ar.edu.utn.frba.dds.domain.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class PromedioEntreAperturaYCierre implements GeneradorRanking {
    @Override
    public Ranking generarRanking(List<IncidentePorComunidad> incidentes) {
        Ranking ranking = new Ranking();

        Map<Entidad,List<Long>> diccionarioTiemposDeCierre = new HashMap<Entidad, List<Long>>();
        incidentes.stream().forEach(incidentePorComunidad -> {
            agregarTiempo(diccionarioTiemposDeCierre,incidentePorComunidad.getIncidente().obtenerEntidad(),diferenciaMinutos(incidentePorComunidad));
        });

        diccionarioTiemposDeCierre.forEach((entidad, tiempos) -> {
            ranking.agregarEntidad(entidad, ((double)  tiempos.stream().mapToLong(tiempo -> tiempo).sum())/tiempos.size());
        });

        ranking.setDescripcion("Promedio entre apertura y cierre");
        ranking.setFechaHoraCreacion(LocalDateTime.now());
        return ranking;
    }
    private Long diferenciaMinutos(IncidentePorComunidad incidentePorComunidad){
        return ChronoUnit.MINUTES.between(
                incidentePorComunidad.getIncidente().getFechaHoraApertura(),
                incidentePorComunidad.getFechaHoraCierre());
    }

    private void agregarTiempo(Map<Entidad,List<Long>> diccionario, Entidad entidad, Long tiempo){
        if(diccionario.containsKey(entidad)){
            diccionario.get(entidad).add(tiempo);
        }else{
            diccionario.put(entidad, new ArrayList<Long>(List.of(tiempo)));
        }
    }
}

